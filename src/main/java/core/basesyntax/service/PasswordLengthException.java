package core.basesyntax.service;

public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException(String msg) {
        super(msg);
    }
}
