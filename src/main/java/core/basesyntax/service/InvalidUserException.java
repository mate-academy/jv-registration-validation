package core.basesyntax.service;

public class InvalidUserException extends RuntimeException {
    InvalidUserException(String massage) {
        super(massage);
    }
}
