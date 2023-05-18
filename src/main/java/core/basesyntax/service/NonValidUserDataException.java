package core.basesyntax.service;

public class NonValidUserDataException extends RuntimeException {
    public NonValidUserDataException(String message) {
        super(message);
    }
}
