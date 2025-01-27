package core.basesyntax.service;

public class NotValidRegistrationException extends RuntimeException {
    public NotValidRegistrationException(String message) {
        super(message);
    }
}
