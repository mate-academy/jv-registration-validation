package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (!isUserAlreadyExist(user)
                && isLoginValid(user.getLogin())
                && isPasswordValid(user.getPassword())
                && userAgeCheck(user.getAge())) {
            storageDao.add(user);
        }
        return user;
    }

    private boolean isUserAlreadyExist(User user) {
        if (user != null && storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User already have an account.");
        }
        return false;
    }

    private boolean isLoginValid(String login) {
        if (login == null) {
            throw new InvalidDataException("Login can`t be empty.");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException("Login length should be more than 6. Your length: "
                    + login.length());
        }
        if (containNonAlphanumeric(login)) {
            throw new InvalidDataException("Your login contains unsupported characters.");
        }
        return true;
    }

    private boolean isPasswordValid(String password) {
        if (password == null) {
            throw new InvalidDataException("Password can`t be empty.");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password length should be more than 6. Your length: "
                    + password.length());
        }
        if (!containNonAlphanumeric(password)
                && containUppercaseAndNumeric(password)) {
            throw new InvalidDataException("""
                    Weak password!
                    Your password MUST contain at least:
                    - 1 special character
                    - 1 UpperCase
                    - 1 Numeric character.""");
        }
        return true;
    }

    private boolean userAgeCheck(Integer age) {
        if (age == null) {
            throw new InvalidDataException("User age can`t be empty.");
        }
        if (age <= 0) {
            throw new InvalidDataException("Wrong age! You never born, "
                    + "why you try to register here?:|");
        }
        if (age < MIN_USER_AGE) {
            throw new InvalidDataException("You not allowed to be registered. "
                    + "You age must be more than 18. Your age: " + age);
        }
        return true;
    }

    private boolean containNonAlphanumeric(String input) {
        String regex = ".*[^a-zA-Z0-9].*";
        return input.matches(regex);
    }

    private boolean containUppercaseAndNumeric(String input) {
        String regex = ".*[A-Z0-9].*";
        return input.matches(regex);
    }
}
