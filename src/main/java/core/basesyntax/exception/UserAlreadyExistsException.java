package core.basesyntax.exception;

public class UserAlreadyExistsException extends UserValidationException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
