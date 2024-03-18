package core.basesyntax.service;

import core.basesyntax.exeptionForService.RegistrationException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws RegistrationException;
}
