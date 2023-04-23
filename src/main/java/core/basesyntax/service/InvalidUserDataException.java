package core.basesyntax.service;

public class InvalidUserDataException extends Error {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
