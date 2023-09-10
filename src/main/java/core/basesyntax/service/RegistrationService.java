package core.basesyntax.service;

import core.basesyntax.Exception.RegistrationException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws RegistrationException;
}
