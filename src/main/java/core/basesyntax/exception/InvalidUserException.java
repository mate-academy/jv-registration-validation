package core.basesyntax.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String exceptionText) {
        super(exceptionText);
    }
}
