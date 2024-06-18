package core.basesyntax.validators;

import core.basesyntax.exeptions.InvalidUserException;
import core.basesyntax.model.User;

public class UserValidator {
    protected final User user;

    public UserValidator(User user) {
        this.user = user;
    }
    
    public void validateUser() throws InvalidUserException {
        validateForNullableValues();
    }

    private void validateForNullableValues() throws InvalidUserException {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
        if (user.getId() == null) {
            throw new InvalidUserException("User id cannot be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserException("Login cannot be null");
        }
        if (user.getAge() == null) {
            throw new InvalidUserException("Age cannot be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException("Age cannot be null");
        }
    }
}
