package core.basesyntax.service;

public class NotValidData extends RuntimeException {
    public NotValidData(String message) {
        super(message);
    }
}
