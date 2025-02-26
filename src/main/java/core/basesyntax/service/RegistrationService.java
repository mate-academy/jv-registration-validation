package core.basesyntax.service;

import core.basesyntax.exceptions.DataExceptions;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws DataExceptions;
}
