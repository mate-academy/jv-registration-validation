package core.basesyntax.validation;

import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public class UserValidatorImpl implements UserValidator {
    public void validate(User user) throws UserValidationException {
        if (user == null) {
            throw new UserValidationException("User can't be null");
        }

        if (user.getLogin() == null) {
            throw new UserValidationException("Login can't be null");
        } else if (user.getLogin().length() < 6) {
            throw new UserValidationException("Login must be at least 6 characters");
        }

        if (user.getPassword() == null) {
            throw new UserValidationException("Password can't be null");
        } else if (user.getPassword().length() < 6) {
            throw new UserValidationException("Password must be at least 6 characters");
        }

        if (user.getAge() < 18) {
            throw new UserValidationException("User must be at least 18 years old");
        }
    }
}
