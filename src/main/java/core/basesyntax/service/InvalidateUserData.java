package core.basesyntax.service;

public class InvalidateUserData extends RuntimeException {
    public InvalidateUserData(String message) {
        super(message);
    }
}
