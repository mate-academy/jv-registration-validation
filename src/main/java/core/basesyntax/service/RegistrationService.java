package core.basesyntax.service;

import core.basesyntax.model.InvalidDataException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws InvalidDataException;
}
