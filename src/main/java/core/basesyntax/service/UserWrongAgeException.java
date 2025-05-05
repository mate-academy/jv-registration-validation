package core.basesyntax.service;

public class UserWrongAgeException extends RuntimeException {
    public UserWrongAgeException(String message) {
        super(message);
    }
}
