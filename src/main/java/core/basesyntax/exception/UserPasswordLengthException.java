package core.basesyntax.exception;

public class UserPasswordLengthException extends RuntimeException {
    public UserPasswordLengthException(String message) {
        super(message);
    }
}
