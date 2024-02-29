package core.basesyntax.exeption.password;

import core.basesyntax.exeption.RegistrationServiceException;

public class NullPasswordException extends RegistrationServiceException {
    public NullPasswordException() {
        super("Password cannot be null");
    }
}
