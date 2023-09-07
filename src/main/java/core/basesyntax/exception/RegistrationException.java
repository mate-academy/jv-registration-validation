package core.basesyntax.exception;

public class WrongValidationException extends RuntimeException {
    public WrongValidationException(String message) {
        super(message);
    }
}
