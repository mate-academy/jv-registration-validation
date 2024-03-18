package core.basesyntax.service;

import core.basesyntax.exeptionforservice.RegistrationException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws RegistrationException;
}
