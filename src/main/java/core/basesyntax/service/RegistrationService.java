package core.basesyntax.service;

import core.basesyntax.exception.AgeRestrictionException;
import core.basesyntax.exception.InputValueException;
import core.basesyntax.exception.LoginLengthException;
import core.basesyntax.exception.PasswordLengthException;
import core.basesyntax.exception.UserAlreadyExistsException;
import core.basesyntax.model.User;

public interface RegistrationService {
    User register(User user) throws UserAlreadyExistsException,
            LoginLengthException, PasswordLengthException,
            InputValueException, AgeRestrictionException;
}
