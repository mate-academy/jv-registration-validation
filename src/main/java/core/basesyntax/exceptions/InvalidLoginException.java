package core.basesyntax.exceptions;

public class InvalidLoginException extends RegistrationException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
