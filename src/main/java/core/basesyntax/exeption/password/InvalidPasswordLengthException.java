package core.basesyntax.exeption.password;

import core.basesyntax.exeption.RegistrationServiceException;

public class InvalidPasswordLengthException extends RegistrationServiceException {
    public InvalidPasswordLengthException(int length) {
        super("The password length must be at least 6 characters and no more than 20. "
                + "Your length: " + length);

    }
}
