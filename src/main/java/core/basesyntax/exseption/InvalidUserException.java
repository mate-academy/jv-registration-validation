package core.basesyntax.exseption;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String massage) {
        super(massage);
    }
}
