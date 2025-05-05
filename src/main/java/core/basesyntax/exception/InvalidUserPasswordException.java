package core.basesyntax.exception;

public class InvalidUserPasswordException extends UserValidationException {
    public InvalidUserPasswordException(String message) {
        super(message);
    }
}
