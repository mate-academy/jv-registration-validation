package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final String INVALID_DATA = "Invalid user data."
            + " Please check login, password, and age.";
    private static final String EXISTS_MSG = "User with this login already exists.";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    public void validateUser(User user) {
        validateNotNull(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        validateExistingUser(user.getLogin());
    }

    private void validateNotNull(User user) {
        if (user == null
                || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null
        ) {
            throw new InvalidUserException(INVALID_DATA);
        }
    }

    private void validateLogin(String login) {
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException(INVALID_DATA);
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException(INVALID_DATA);
        }
    }

    private void validateAge(int age) {
        if (age < MIN_AGE) {
            throw new InvalidUserException(INVALID_DATA);
        }
    }

    private void validateExistingUser(String login) {
        User existingUser = storageDao.get(login);
        if (existingUser != null) {
            throw new InvalidUserException(EXISTS_MSG);
        }
    }
}
