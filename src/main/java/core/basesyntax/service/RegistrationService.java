package core.basesyntax.service;

import core.basesyntax.exception.CantAddUserException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws CantAddUserException;
}
