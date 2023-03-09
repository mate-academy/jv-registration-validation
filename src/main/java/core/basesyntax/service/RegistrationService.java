package core.basesyntax.service;

import core.basesyntax.exceptions.AuthorizationError;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws AuthorizationError;
}
