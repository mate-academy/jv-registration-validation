package core.basesyntax.service;

public class IncorrectDataForRegistrationException extends RuntimeException {
    public IncorrectDataForRegistrationException(String message) {
        super(message);
    }
}
