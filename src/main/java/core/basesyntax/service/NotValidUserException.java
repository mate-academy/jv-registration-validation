package core.basesyntax.service;

public class NotValidUserException extends RuntimeException {
    public NotValidUserException(String message) {
        super(message);
    }
}
