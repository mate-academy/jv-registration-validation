package core.basesyntax.service;

public class BadDataValidationException extends RuntimeException {
    public BadDataValidationException(String message) {
        super(message);
    }
}
