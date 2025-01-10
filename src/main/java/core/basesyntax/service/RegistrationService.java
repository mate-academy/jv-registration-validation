package core.basesyntax.service;

import core.basesyntax.exeptions.InvalidCredentialsException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws InvalidCredentialsException;
}
