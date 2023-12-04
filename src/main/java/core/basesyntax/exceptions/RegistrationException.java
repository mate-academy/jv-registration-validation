package core.basesyntax.exceptions;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String errorMessage) {
        super(errorMessage);
    }
}
