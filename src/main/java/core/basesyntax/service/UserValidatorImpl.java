package core.basesyntax.service;

import core.basesyntax.exception.InvalidUserAgeException;
import core.basesyntax.exception.InvalidUserLoginException;
import core.basesyntax.exception.InvalidUserPasswordException;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public class UserValidatorImpl implements UserValidator {
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;

    @Override
    public void validate(User user) {
        if (user == null) {
            throw new UserValidationException("Invalid user, can't be null");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
    }

    private void validateLogin(String login) {
        if (login == null || login.length() < MIN_LENGTH_LOGIN) {
            throw new InvalidUserLoginException(
                    "Invalid login, must be at least " + MIN_LENGTH_LOGIN + " characters long"
            );
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < MIN_LENGTH_PASSWORD) {
            throw new InvalidUserPasswordException(
                    "Invalid password, must be at least " + MIN_LENGTH_PASSWORD + " characters long"
            );
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new InvalidUserAgeException("Invalid age, should be over " + MIN_AGE);
        }
    }
}
