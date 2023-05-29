package core.basesyntax.service;

import core.basesyntax.CustomException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws CustomException;
}
