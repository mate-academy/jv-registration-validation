package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidLoginException;
import core.basesyntax.exception.InvalidAgeExeption;
import core.basesyntax.exception.InvalidPasswordException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 4;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkExistUserInDataBase(user.getLogin());
        validatePassword(user.getPassword());
        passwordNullValidation(user.getPassword());
        ageValidation(user.getAge());
        ageNullValidation(user.getAge());
        return storageDao.add(user);
    }

    private void checkExistUserInDataBase(String login) {
        loginValidation(login);
        loginNullValidation(login);
        if (storageDao.get(login) != null) {
            throw new InvalidLoginException("consumer with " + login + "login is already existing");
        }
    }

    private void loginValidation(String login) {
        if (login.length() < MINIMAL_LOGIN_LENGTH) {
            throw new InvalidLoginException(
                    "your login must be more than " + MINIMAL_LOGIN_LENGTH + " characters");
        }
    }

    private void loginNullValidation(String login) {
        if (login == null) {
            throw new InvalidLoginException("Login can`t be Null");
        }
    }

    private void validatePassword(String password) {
        if (password.length() <= MINIMAL_PASSWORD_LENGTH) {
            throw new InvalidPasswordException(
                    "password must be longer than " + MINIMAL_PASSWORD_LENGTH + " characters");
        }
    }

    private void passwordNullValidation(String password) {
        if (password == null) {
            throw new InvalidPasswordException("password can`t be null");
        }
    }

    private void ageValidation(int age) {
        if (age < MINIMAL_AGE) {
            throw new InvalidAgeExeption(
                    "User age must be at least " + MINIMAL_AGE);
        }
    }
    private void ageNullValidation(int age) {
        Integer number = age;
        if (number == null) {
            throw new InvalidAgeExeption("age can`t be null");
        }
    }
}
