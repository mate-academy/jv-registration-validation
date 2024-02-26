package core.basesyntax.service;

public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException(int minLength) {
        super("Password should be at least " + minLength + " characters long.");
    }
}
