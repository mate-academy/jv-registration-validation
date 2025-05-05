package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.RegistrationException;
import core.basesyntax.model.User;

public class UserValidator {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;

    private final StorageDaoImpl storageDao;

    public UserValidator() {
        storageDao = new StorageDaoImpl();
    }

    public void validate(User user) {
        validateAge(user.getAge());
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
    }

    private void validateAge(int age) {
        if (Integer.valueOf(age) == null) {
            throw new RegistrationException("Age is null. "
                    + "Enter correct age.");
        }
        if (isAgeHighEnough(age)) {
            throw new RegistrationException("Incorrect age. "
                    + "Enter age between 18 and 150.");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login is null. "
                    + "Enter correct login.");
        }
        if (isLoginLongEnough(login)) {
            throw new RegistrationException("Login is too short."
                    + "Enter login longer than 6 characters.");
        }
        if (isUserLoginAlreadyExist(login)) {
            throw new RegistrationException("There is user with the same login"
                    + "Enter login that not exist.");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password is null. "
                    + "Enter correct password.");
        }
        if (isPasswordLongEnough(password)) {
            throw new RegistrationException("Password is too short. "
                    + "Enter password longer than 6 characters.");
        }
    }

    private boolean isAgeHighEnough(int age) {
        return age < MIN_AGE;
    }

    private boolean isLoginLongEnough(String login) {
        return login.length() < MIN_LOGIN_LENGTH;
    }

    private boolean isUserLoginAlreadyExist(String login) {
        return storageDao.get(login) != null;
    }

    private boolean isPasswordLongEnough(String password) {
        return password.length() < MIN_PASSWORD_LENGTH;
    }
}
