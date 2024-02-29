package core.basesyntax.exeption.login;

import core.basesyntax.exeption.RegistrationServiceException;

public class NullLoginException extends RegistrationServiceException {
    public NullLoginException() {
        super("Login cannot be null");
    }
}
