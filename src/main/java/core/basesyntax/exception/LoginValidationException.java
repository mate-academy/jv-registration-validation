package core.basesyntax.exception;

public class LoginValidationException extends RuntimeException {
    public LoginValidationException(String message) {
        super((message));
    }

    public LoginValidationException() {
    }
}