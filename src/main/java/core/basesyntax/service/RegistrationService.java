package core.basesyntax.service;

import core.basesyntax.exception.AuthenticationException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws AuthenticationException;
}
