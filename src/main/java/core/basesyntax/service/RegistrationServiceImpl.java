package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            return null;
        }
        if (storageDao.get(user.getLogin()) != null) {
            try {
                throw new UserAlreadyExistsException(user.getLogin());
            } catch (UserAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        if (user.getPassword().length() < 6) {
            try {
                throw new PasswordLengthException(6);
            } catch (PasswordLengthException e) {
                throw new RuntimeException(e);
            }
        }
        if (user.getAge() < 6) {
            throw new IllegalArgumentException("The Age of user is not valid");
        }
        return user;
    }

    private class UserAlreadyExistsException extends Exception {
        private String username;

        public UserAlreadyExistsException(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "User with username '" + username + "' already exists.";
        }
    }

    public class PasswordLengthException extends Exception {
        private int minLength;

        public PasswordLengthException(int minLength) {
            this.minLength = minLength;
        }

        @Override
        public String toString() {
            return "Password should be at least " + minLength + " characters long.";
        }
    }
}
