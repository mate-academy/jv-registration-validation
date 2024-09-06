package core.basesyntax.validation;

import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public class UserValidatorImpl implements UserValidator {
    public void validate(User user) throws UserValidationException {
        if (user == null) {
            throw new UserValidationException("User can't be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new UserValidationException("Login must be more than 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new UserValidationException("Password must be more than 6 characters");
        }
        if (user.getAge() < 18) {
            throw new UserValidationException("User must be more than 18 years old");
        }
    }
}
