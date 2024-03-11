package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_PASSWORD_MIN_LENGTH = 6;
    private static final int USER_LOGIN_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserInput(user);
        return storageDao.add(user);
    }

    private void validateUserInput(User user) {
        validateUserLogin(user.getLogin());
        validateUserPassword(user.getPassword());
        validateUserAge(user.getAge());
        validateUserExist(user);
    }

    private void validateUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with login '"
                    + user.getLogin() + "' already exists");
        }
    }

    private void validateUserAge(Integer age) {
        if (age == null) {
            throw new InvalidUserException("User's age can't be 'null'!");
        }
        if (age < USER_MIN_AGE) {
            throw new InvalidUserException("Users under "
                    + USER_MIN_AGE + " are not allowed!");
        }
    }

    private void validateUserLogin(String login) {
        if (login == null) {
            throw new InvalidUserException("User's login can't be 'null'!");
        }
        if (login.length() < USER_LOGIN_MIN_LENGTH) {
            throw new InvalidUserException("Login must be at least "
                    + USER_LOGIN_MIN_LENGTH + " characters");
        }
    }

    private void validateUserPassword(String password) {
        if (password == null) {
            throw new InvalidUserException("User's password can't be 'null'!");
        }
        if (password.length() < USER_PASSWORD_MIN_LENGTH) {
            throw new InvalidUserException("Password must be at least "
                    + USER_PASSWORD_MIN_LENGTH + " characters");
        }
    }
}
