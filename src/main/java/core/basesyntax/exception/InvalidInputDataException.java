package core.basesyntax.exception;

public class RegistrationException extends RuntimeException {
    public InvalidInputDataException(String message) {
        super(message);
    }
}
