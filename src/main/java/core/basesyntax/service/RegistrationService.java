package core.basesyntax.service;

import core.basesyntax.service.model.User;

public interface RegistrationService {
    User register(User user) throws RegistrationException;
}
