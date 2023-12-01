package core.basesyntax.service;

import core.basesyntax.exeption.InvalidUserDataException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws InvalidUserDataException;

    boolean userValidator(User user) throws InvalidUserDataException;

    boolean foundUserInDatabase(User user);
}
