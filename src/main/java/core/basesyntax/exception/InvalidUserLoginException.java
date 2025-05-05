package core.basesyntax.exception;

public class InvalidUserLoginException extends UserValidationException {
    public InvalidUserLoginException(String message) {
        super(message);
    }
}
