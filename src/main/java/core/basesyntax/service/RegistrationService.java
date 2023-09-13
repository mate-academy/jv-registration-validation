package core.basesyntax.service;

import core.basesyntax.exceptions.IncorrectInputDataException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws IncorrectInputDataException;
}
