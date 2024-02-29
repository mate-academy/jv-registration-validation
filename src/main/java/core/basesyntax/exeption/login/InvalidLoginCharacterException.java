package core.basesyntax.exeption.login;

import core.basesyntax.exeption.RegistrationServiceException;

public class InvalidLoginCharacterException extends RegistrationServiceException {
    public InvalidLoginCharacterException(String unacceptableSymbols) {
        super("You cannot use these characters for login: " + unacceptableSymbols);
    }
}
