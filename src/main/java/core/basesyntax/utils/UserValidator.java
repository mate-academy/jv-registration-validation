package core.basesyntax.utils;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;

public class UserValidator {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;

    public void validate(User user) {
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("User age must not be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be at least 18 years old");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
    }
}
