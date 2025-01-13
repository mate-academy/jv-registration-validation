package core.basesyntax.service;

public class UserRepeatingException extends RuntimeException {
    public UserRepeatingException(String message) {
        super(message);
    }
}
