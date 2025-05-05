package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
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

        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password: \"" + user.getPassword()
                    + "\" has less than "
                    + MINIMUM_PASSWORD_LENGTH + " characters");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("The indicated age is less than " + MINIMUM_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exist "
                    + user.getLogin());
        }
    }
}
