package core.basesyntax.service;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String massage) {
        super(massage);
    }
}
