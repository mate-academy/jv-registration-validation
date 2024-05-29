package core.basesyntax.service;

public class IncorrectUserDataException extends RuntimeException {
    public IncorrectUserDataException(String message) {
        super(message);
    }
}
