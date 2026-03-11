package core.basesyntax.exception;

public class InvalidRegisterArgumentException extends RuntimeException {
    public InvalidRegisterArgumentException() {
        super();
    }

    public InvalidRegisterArgumentException(String message) {
        super(message);
    }
}
