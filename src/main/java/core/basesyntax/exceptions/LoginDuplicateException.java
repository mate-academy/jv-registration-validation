package core.basesyntax.exceptions;

public class LoginDuplicateException extends RuntimeException {
    public LoginDuplicateException(String message) {
        super(message);
    }
}
