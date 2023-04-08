package core.basesyntax.service;

public class NotNullParameterUncheckedException extends RuntimeException {
    public NotNullParameterUncheckedException(String message) {
        super(message);
    }
}
