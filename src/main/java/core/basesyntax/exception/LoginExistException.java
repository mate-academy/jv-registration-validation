package core.basesyntax.exception;

public class LoginExistException extends RuntimeException {
    public LoginExistException(String message) {
        super(message);
    }
}
