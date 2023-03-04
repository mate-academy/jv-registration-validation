package core.basesyntax.service;

public class UserPasswordTooShortException extends RuntimeException {
    public UserPasswordTooShortException() {
        super("User's password has less characters than 6");
    }
}
