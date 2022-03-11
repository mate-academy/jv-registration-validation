package core.basesyntax.service;

public class NullDataException extends RuntimeException {
    public NullDataException(String data) {
        super("Can't register user with null " + data);
    }
}
