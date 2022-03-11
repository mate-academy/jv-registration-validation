package core.basesyntax.service;

public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException() {
        super("Can't register user whose password length is less than 6 symbols");
    }
}
