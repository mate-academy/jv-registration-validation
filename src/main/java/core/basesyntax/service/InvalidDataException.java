package core.basesyntax.service;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super("Invalid data");
    }
}
