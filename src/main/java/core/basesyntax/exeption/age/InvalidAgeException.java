package core.basesyntax.exeption.age;

import core.basesyntax.exeption.RegistrationServiceException;

public class InvalidAgeException extends RegistrationServiceException {
    public InvalidAgeException(String message) {
        super(message);
    }
}
