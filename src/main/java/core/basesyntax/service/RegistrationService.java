package core.basesyntax.service;

import core.basesyntax.exception.RegistrationServiceImplException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user);
}
