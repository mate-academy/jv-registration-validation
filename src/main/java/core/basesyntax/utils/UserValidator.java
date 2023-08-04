package core.basesyntax.utils;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;

public class UserValidator {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    public static void checkAge(User user) {
        if (user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be at least 18 years old");
        }
    }
    public static void checkLogin (User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
    }
    public static void checkPassword (User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
    }
}
