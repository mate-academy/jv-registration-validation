package core.basesyntax.service;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws ValidationException;
}
