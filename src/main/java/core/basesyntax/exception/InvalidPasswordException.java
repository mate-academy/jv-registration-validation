package core.basesyntax.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String massage) {
        super(massage);
    }
}
