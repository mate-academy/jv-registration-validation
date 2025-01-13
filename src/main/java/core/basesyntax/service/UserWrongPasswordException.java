package core.basesyntax.service;

public class UserWrongPasswordException extends RuntimeException {
    public UserWrongPasswordException(String message) {
        super(message);
    }
}

