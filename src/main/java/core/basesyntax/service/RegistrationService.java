package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws InvalidDataException;
}
