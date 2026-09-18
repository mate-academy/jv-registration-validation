package core.basesyntax.service;

public class InvalidUserData extends RuntimeException {
    public InvalidUserData(String message) {
        super(message);
    }
}
