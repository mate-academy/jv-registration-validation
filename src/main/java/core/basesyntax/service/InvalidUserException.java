package core.basesyntax.service;

public class InvalidUserException extends RuntimeException {
    private InvalidUserException (String massage) {
        super(massage);
    }
}
