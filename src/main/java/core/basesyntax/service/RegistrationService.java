package core.basesyntax.service;

import core.basesyntax.exeption.InvalidUserException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user);
}
