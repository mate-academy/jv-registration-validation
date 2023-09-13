package core.basesyntax.service;

import core.basesyntax.model.User;
import core.basesyntax.unchekedException.IncorrectInputDataException;

public interface RegistrationService {
    User register(User user) throws IncorrectInputDataException;
}
