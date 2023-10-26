package core.basesyntax.exception;

public class NotValidUserException extends RuntimeException {
    public NotValidUserException(String message) {
        super(message);
    }
}
