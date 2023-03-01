package core.basesyntax.exception;

public class UserIsNotValidException extends RuntimeException {
    public UserIsNotValidException(String message) {
        super(message);
    }
}
