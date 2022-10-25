package core.basesyntax.exseption;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String massage) {
        super(massage);
    }
}
