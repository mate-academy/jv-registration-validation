package core.basesyntax.exseption;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String massage) {
        super(massage);
    }
}
