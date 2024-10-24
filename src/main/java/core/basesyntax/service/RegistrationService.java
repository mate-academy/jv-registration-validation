package core.basesyntax.service;

import core.basesyntax.exception.RegisterException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws RegisterException;
}
