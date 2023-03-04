package core.basesyntax.service;

public class UserTooOldException extends RuntimeException {
    public UserTooOldException() {
        super("User is too old. Nobody in the world can't have such age");
    }
}
