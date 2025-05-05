package core.basesyntax.customexception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String massage) {
        super(massage);
    }
}
