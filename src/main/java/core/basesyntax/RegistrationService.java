package core.basesyntax;

import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws UserIsNotValidException;
}
