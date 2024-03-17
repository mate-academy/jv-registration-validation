package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidAgeException;
import core.basesyntax.exception.LoginValidateException;
import core.basesyntax.exception.PasswordValidateException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_LOGIN_LENGTH = 4;
    private static final int FIRST_LOGIN_CHARACTER_INDEX = 0;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkExistUserInDataBase(user.getLogin());
        ageValidation(user.getAge());
        validatePassword(user.getPassword());
        return storageDao.add(user);
    }

    private void loginValidation(String login) {
        if (login == null || login.length() <= MINIMAL_LOGIN_LENGTH) {
            throw new LoginValidateException("your login must be more than 4 characters");
        } else if (!Character.isLetter(login.charAt(FIRST_LOGIN_CHARACTER_INDEX))) {
            throw new LoginValidateException("your login should start from the letter");
        }
    }

    private void checkExistUserInDataBase(String login) {
        loginValidation(login);
        if (storageDao.get(login) != null) {
            throw new LoginValidateException("consumer with " + login + "login is already "
                    + "exist please try another one login try to login with password");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() <= MINIMAL_PASSWORD_LENGTH) {
            throw new PasswordValidateException("password must be longer than six characters");
        }
    }

    private void ageValidation(int age) {
        if (age < MINIMAL_AGE) {
            throw new InvalidAgeException(
                    "User age must be at least " + MINIMAL_AGE);
        }
    }
}
