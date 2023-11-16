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

    public void validateAge(User user) throws RegistrationException {
        if (user.getAge() == null) {
            throw new RegistrationException("Age is null. "
                    + "Enter correct age.");
        }
        if (isAgeHighEnough(user)) {
            throw new RegistrationException("Incorrect age. "
                    + "Enter age between 18 and 150.");
        }
    }

    public void validateLogin(User user) throws RegistrationException {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login is null. "
                    + "Enter correct login.");
        }
        if (isLoginLongEnough(user)) {
            throw new RegistrationException("Login is too short."
                    + "Enter login longer than 6 characters.");
        }
        if (isUserLoginAlreadyExist(user)) {
            throw new RegistrationException("There is user with the same login"
                    + "Enter login that not exist.");
        }
    }

    public void validatePassword(User user) throws RegistrationException {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password is null. "
                    + "Enter correct password.");
        }
        if (isPasswordLongEnough(user)) {
            throw new RegistrationException("Password is too short. "
                    + "Enter password longer than 6 characters.");
        }
    }

    private boolean isAgeHighEnough(User user) {
        return user.getAge() < MIN_AGE;
    }

    private boolean isLoginLongEnough(User user) {
        return user.getLogin()
                .length() < MIN_LOGIN_LENGTH;
    }

    private boolean isUserLoginAlreadyExist(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private boolean isPasswordLongEnough(User user) {
        return user.getPassword()
                .length() < MIN_PASSWORD_LENGTH;
    }
}
