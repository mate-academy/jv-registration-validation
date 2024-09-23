package core.basesyntax.exceptions;

public class LoginTooShortException extends RuntimeException {
    public LoginTooShortException(String message) {
        super(message);
    }
}
