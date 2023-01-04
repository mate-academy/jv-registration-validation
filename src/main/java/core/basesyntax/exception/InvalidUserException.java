package core.basesyntax.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String messages) {
        super(messages);
    }
}
