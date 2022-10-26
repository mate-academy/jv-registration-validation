package core.basesyntax.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String massage) {
        super(massage);
    }
}
