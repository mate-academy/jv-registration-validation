package core.basesyntax.exception;

public class UserLoginExistsException extends RuntimeException {
    public UserLoginExistsException(String message) {
        super(message);
    }
}
