package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        validateRegistration(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RuntimeException("Login can't be empty");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Not valid password");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Not valid age");
        }
    }

    private void validateRegistration(User user) {
        User userByLogin = storageDao.get(user.getLogin());
        if (userByLogin != null) {
            if (userByLogin.equals(user)) {
                throw new RuntimeException("User " + user.toString()
                        + " already exists in the storage");
            } else {
                throw new RuntimeException("Login " + user.getLogin()
                        + " is already taken by another user");
            }
        }
    }
}
