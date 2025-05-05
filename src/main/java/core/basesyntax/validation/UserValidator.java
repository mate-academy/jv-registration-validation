package core.basesyntax.validation;

import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public interface UserValidator {
    void validate(User user) throws UserValidationException;
}
