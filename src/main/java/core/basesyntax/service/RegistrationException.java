package core.basesyntax.service;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String errorMessage) {
        super(errorMessage);
    }
}
