package core.basesyntax.exceptions;

public class NotCorrectPasswordException extends RuntimeException {
    public NotCorrectPasswordException(String message) {
        super(message);
    }
}
