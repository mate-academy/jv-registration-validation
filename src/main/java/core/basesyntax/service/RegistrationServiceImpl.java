package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.regex.Pattern;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_LENGTH = 6;
    private static final int PASSWORD_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null) {
            validateLogin(user.getLogin());
            validateUserAge(user.getAge());
            validatePassword(user.getPassword());
            return storageDao.add(user);
        }
        throw new UserValidationException("No user data");
    }

    private void validateUserAge(Integer age) {
        if (age < USER_MIN_AGE) {
            throw new UserValidationException("To register user must be 18 or older");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new UserValidationException("No login accepted");
        }
        if (storageDao.get(login) != null) {
            throw new UserValidationException("User with login - " + login + " already exists");
        }
        if (login.length() < LOGIN_LENGTH) {
            throw new UserValidationException("Login must be not shorter than " + LOGIN_LENGTH);
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new UserPasswordValidationException("No login accepted");
        }
        if (password.length() < PASSWORD_LENGTH) {
            throw new UserPasswordValidationException("Password must be not shorter than "
                    + PASSWORD_LENGTH);
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            throw new UserPasswordValidationException("Password must contain digit");
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            throw new UserPasswordValidationException("Password must contain upper-case letter");
        }
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            throw new UserPasswordValidationException("Password must contain lower-case letter");
        }
        if (!Pattern.compile("[!@#$%^&*()_+={}|<>?]").matcher(password).find()) {
            throw new UserPasswordValidationException("Password must contain special symbol");
        }
    }
}
