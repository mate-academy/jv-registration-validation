package core.basesyntax.service;

import core.basesyntax.exeptions.ValidationException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws ValidationException;
}
