package core.basesyntax.exeption.password;

import core.basesyntax.exeption.RegistrationServiceException;

public class InvalidPasswordCharacterException extends RegistrationServiceException {
    public InvalidPasswordCharacterException(String unacceptableSymbols) {
        super("You cannot use these characters for password: " + unacceptableSymbols);
    }
}
