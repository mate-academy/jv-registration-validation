package core.basesyntax.service;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String message, Throwable e) {
        super(message, e);
    }
}
