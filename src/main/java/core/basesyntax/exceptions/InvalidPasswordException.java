package core.basesyntax.exceptions;

public class InvalidPasswordException extends RegistrationException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
