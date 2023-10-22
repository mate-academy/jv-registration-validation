package core.basesyntax.exception;

public class LoginExistingException extends RuntimeException {
    public LoginExistingException(String message) {
        super(message);
    }
}
