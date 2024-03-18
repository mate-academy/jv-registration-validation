package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidAgeException;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 4;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullUser(user);
        checkExistUserInDataBase(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkNullUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
    }

    private void checkExistUserInDataBase(String login) {
        validateLogin(login);
        if (storageDao.get(login) != null) {
            throw new InvalidLoginException(
                    "consumer with " + login + "login is already existing");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new InvalidLoginException("Login can`t be Null");
        }
        if (login.length() < MINIMAL_LOGIN_LENGTH) {
            throw new InvalidLoginException(
                    "your login must be more than " + MINIMAL_LOGIN_LENGTH + " characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new InvalidPasswordException("password can`t be null");
        }
        if (password.length() < MINIMAL_PASSWORD_LENGTH) {
            throw new InvalidPasswordException(
                    "password must be longer than " + MINIMAL_PASSWORD_LENGTH + " characters");
        }
    }

    private void validateAge(int age) {
        if (age < MINIMAL_AGE) {
            throw new InvalidAgeException(
                    "age should be " + MINIMAL_AGE);
        }
    }
}
