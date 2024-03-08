package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_PASSWORD_MIN_LENGTH = 6;
    private static final int USER_LOGIN_MIN_LENGTH = 6;

    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateUserInput(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with login '"
                    + user.getLogin() + "' already exists");
        }
        return storageDao.add(user);
    }

    private void validateUserInput(User user) {
        validateUserLogin(user.getLogin());
        validateUserPassword(user.getPassword());
        validateUserAge(user.getAge());
    }

    private void validateUserAge(Integer age) {
        if (age == null || age < USER_MIN_AGE) {
            throw new InvalidUserException("Users under "
                    + USER_MIN_AGE + " are not allowed!");
        }
    }

    private void validateUserLogin(String login) {
        if (login == null || login.length() < USER_LOGIN_MIN_LENGTH) {
            throw new InvalidUserException("Login must be at least "
                    + USER_LOGIN_MIN_LENGTH + " characters");
        }
    }

    private void validateUserPassword(String password) {
        if (password == null || password.length() < USER_PASSWORD_MIN_LENGTH) {
            throw new InvalidUserException("Password must be at least "
                    + USER_PASSWORD_MIN_LENGTH + " characters");
        }
    }
}
