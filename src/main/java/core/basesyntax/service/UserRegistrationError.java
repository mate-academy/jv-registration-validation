package core.basesyntax.service;

public class UserRegistrationError extends RuntimeException {
    public UserRegistrationError(String message) {
        super(message);
    }
}
