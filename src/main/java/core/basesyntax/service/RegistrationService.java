package core.basesyntax.service;

import core.basesyntax.exceptions.UserInvalidDataException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws UserInvalidDataException;
}
