package core.basesyntax.service;

import core.basesyntax.exception.RegistationException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user);
}
