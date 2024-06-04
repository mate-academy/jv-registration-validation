package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    private boolean userAlreadyExist(User user) {
        if (user != null && storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User already have an account.");
        }
        return false;
    }

    private boolean loginQualityCheck(User user) {
        String userLogin = user.getLogin();
        if (userLogin == null) {
            throw new InvalidDataException("Login can`t be empty.");
        }
        if (userLogin.length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Login length should be more than 6. Your length: "
                    + userLogin.length());
        }
        if (containNonAlphanumeric(userLogin)) {
            throw new InvalidDataException("Your login contains unsupported characters.");
        }
        return true;
    }

    private boolean passwordQualityCheck(User user) {
        String userPassword = user.getPassword();
        if (userPassword == null) {
            throw new InvalidDataException("Password can`t be empty.");
        }
        if (userPassword.length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password length should be more than 6. Your length: "
                    + userPassword.length());
        }
        if (!containNonAlphanumeric(userPassword)
                && containUppercaseAndNumeric(userPassword)) {
            throw new InvalidDataException("""
                    Weak password!
                    Your password MUST contain at least:
                    - 1 special character
                    - 1 UpperCase
                    - 1 Numeric character.""");
        }
        return true;
    }

    private boolean userAgeCheck(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("User age can`t be empty.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidDataException("You not allowed to be registered. "
                    + "You age must be more than 18. Your age: " + user.getAge());
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

    @Override
    public User register(User user) {
        if (!userAlreadyExist(user)
                && loginQualityCheck(user)
                && passwordQualityCheck(user)
                && userAgeCheck(user)) {
            storageDao.add(user);
        }
        return user;
    }
}
