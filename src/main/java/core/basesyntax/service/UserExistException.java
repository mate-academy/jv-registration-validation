package core.basesyntax.service;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
