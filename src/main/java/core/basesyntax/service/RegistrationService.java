package core.basesyntax.service;

import core.basesyntax.exeptions.InvalidUserException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws InvalidUserException;
}
