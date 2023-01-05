package core.basesyntax.customException;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException (String massage) {
        super(massage);
    }
}
