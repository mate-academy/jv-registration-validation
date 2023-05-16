package core.basesyntax.validator;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;

public class UserValidator implements Validator {
    private static final String LOGIN_PATTERN = "(\\w+[\\.\\-_]?\\w+)+";
    private static final String PASSWORD_PATTERN = "(\\w+[\\-_@]?\\w+)+";
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_ALLOWED_AGE = 18;
    private static final int MAX_ALLOWED_AGE = 100;
    private StorageDao storage;

    @Override
    public boolean isValid(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be Null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be Null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be Null");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be Null");
        }
        return isLoginValid(user.getLogin())
                && isAgeValid(user.getAge())
                && isPasswordValid(user.getPassword());
    }

    private boolean isLoginValid(String login) {
        login = login.strip();
        if (login.length() == 0) {
            throw new InvalidDataException("The login is empty!");
        }
        if (!login.matches(LOGIN_PATTERN)) {
            throw new InvalidDataException("Login '" + login + "' contains invalid character");
        }
        storage = new StorageDaoImpl();
        return storage.get(login) == null;
    }

    private boolean isPasswordValid(String password) {
        password = password.strip();
        if (password.length() == 0) {
            throw new InvalidDataException("The password is empty!");
        }
        if (!password.matches(PASSWORD_PATTERN)) {
            throw new InvalidDataException("Password '" + password
                    + "' contains invalid character");
        }
        return password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean isAgeValid(int age) {
        return age >= MIN_ALLOWED_AGE && age <= MAX_ALLOWED_AGE;
    }
}
