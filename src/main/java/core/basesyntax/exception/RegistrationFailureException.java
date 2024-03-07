package core.basesyntax.exception;

public class RegistrationFailureException extends RuntimeException {
    public RegistrationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
