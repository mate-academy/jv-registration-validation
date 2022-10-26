package core.basesyntax.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String massage) {
        super(massage);
    }
}
