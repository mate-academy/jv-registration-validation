package core.basesyntax.service;

import core.basesyntax.exceptions.AlreadyRegisteredException;
import core.basesyntax.exceptions.ValidDataException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws AlreadyRegisteredException, ValidDataException;
}
