package core.basesyntax.service;

public class UserShortAgeException extends RuntimeException {
    public UserShortAgeException() {
        super("User's age is less than 18");
    }
}
