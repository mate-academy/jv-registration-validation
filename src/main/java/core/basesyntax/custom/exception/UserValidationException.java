package core.basesyntax.custom.exception;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String errorMessage) {
        super(errorMessage);
    }
}
