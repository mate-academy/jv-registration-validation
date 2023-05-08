package core.basesyntax.service;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String massage) {
        super(massage);
    }
}
