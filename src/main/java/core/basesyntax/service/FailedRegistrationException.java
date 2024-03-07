package core.basesyntax.service;

public class FailedRegistrationException extends RuntimeException {
    public FailedRegistrationException(String message) {
        super(message);
    }
}
