package core.basesyntax.exeption.login;

import core.basesyntax.exeption.RegistrationServiceException;

public class InvalidLoginLengthException extends RegistrationServiceException {
    public InvalidLoginLengthException(int length) {
        super("The login length must be at least 6 characters and no more than 20."
                + " Your length: " + length);
    }
}
