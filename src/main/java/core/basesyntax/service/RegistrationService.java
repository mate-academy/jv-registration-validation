package core.basesyntax.service;

import core.basesyntax.model.User;
import core.basesyntax.validators.RegistrationValidatorException;

public interface RegistrationService {
    User register(User user) throws RegistrationValidatorException;
}
