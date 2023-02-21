package core.basesyntax.service;

public class UserIsNotValidException extends RuntimeException {
    public UserIsNotValidException(String message) {
        super(message);
    }
}
