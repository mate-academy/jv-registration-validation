package core.basesyntax.service;

public class UserWrongFieldsException extends RuntimeException {
    public UserWrongFieldsException(String message) {
        super(message);
    }
}
