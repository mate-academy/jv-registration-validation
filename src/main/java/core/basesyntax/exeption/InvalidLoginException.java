package core.basesyntax.exeption;

public class InvalidLoginException extends InvalidUserException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
