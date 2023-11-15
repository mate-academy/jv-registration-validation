package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptions.RegistrationException;
import core.basesyntax.model.User;

public class UserValidator {
    private static final StorageDaoImpl storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_OR_PASSWORD_LENGTH = 6;

    public void validate(User user) throws RegistrationException {
        if (nullAge(user)) {
            throw new RegistrationException("Age is null. "
                    + "Enter correct age.");
        }
        if (incorrectAge(user)) {
            throw new RegistrationException("Incorrect age. "
                    + "Enter age between 18 and 150.");
        }
        if (nullLogin(user)) {
            throw new RegistrationException("Login is null. "
                    + "Enter correct login.");
        }
        if (shortLogin(user)) {
            throw new RegistrationException("Login is too short."
                    + "Enter login longer than 6 characters.");
        }
        if (wrongStartsLogin(user)) {
            throw new RegistrationException("Login starts with wrong character"
                    + "Enter login which starts with [a-z|A-Z].");
        }
        if (existingLogin(user)) {
            throw new RegistrationException("There is user with the same login"
                    + "Enter login that not exist.");
        }
        if (nullPassword(user)) {
            throw new RegistrationException("Password is null. "
                    + "Enter correct password.");
        }
        if (shortPassword(user)) {
            throw new RegistrationException("Password is too short. "
                    + "Enter password longer than 6 characters.");
        }
    }

    private static boolean nullAge(User user) {
        return user.getAge() == null;
    }

    private static boolean incorrectAge(User user) {
        return user.getAge() < MIN_AGE;
    }

    private static boolean nullLogin(User user) {
        return user.getLogin() == null;
    }

    private static boolean shortLogin(User user) {
        return user.getLogin()
                .length() < MIN_LOGIN_OR_PASSWORD_LENGTH;
    }

    private static boolean wrongStartsLogin(User user) {
        return Character.toString(user
                .getLogin().charAt(0)).matches("[^a-z|A-Z]");
    }

    private static boolean existingLogin(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private static boolean nullPassword(User user) {
        return user.getPassword() == null;
    }

    private static boolean shortPassword(User user) {
        return user.getPassword()
                .length() < MIN_LOGIN_OR_PASSWORD_LENGTH;
    }
}
