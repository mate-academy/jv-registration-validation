package core.basesyntax.service;

import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws UserValidationException;
}
