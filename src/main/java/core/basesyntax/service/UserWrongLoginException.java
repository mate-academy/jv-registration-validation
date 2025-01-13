package core.basesyntax.service;

public class UserWrongLoginException extends RuntimeException {
    public UserWrongLoginException(String message) {
        super(message);
    }
}
