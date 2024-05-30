package core.basesyntax.exceptions;

public class EmptyLoginException extends RuntimeException {
    public EmptyLoginException(String message) {
        super(message);
    }
}
