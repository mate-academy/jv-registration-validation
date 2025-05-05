package core.basesyntax.exeption;

public class InvalidPasswordException extends InvalidUserException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
