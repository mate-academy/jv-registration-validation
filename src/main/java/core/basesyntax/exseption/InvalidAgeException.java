package core.basesyntax.exseption;

public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(String massage) {
        super(massage);
    }
}
