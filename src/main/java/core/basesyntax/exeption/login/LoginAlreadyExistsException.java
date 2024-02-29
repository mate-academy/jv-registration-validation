package core.basesyntax.exeption.login;

import core.basesyntax.exeption.RegistrationServiceException;

public class LoginAlreadyExistsException extends RegistrationServiceException {
    public LoginAlreadyExistsException() {
        super("This login is already taken");
    }
}
