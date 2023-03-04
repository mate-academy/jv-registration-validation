package core.basesyntax.service;

public class UserAlreadyPresentException extends RuntimeException {
    public UserAlreadyPresentException() {
        super("Such user is already present in the storage");
    }
}
