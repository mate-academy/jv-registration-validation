package core.basesyntax.service;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
