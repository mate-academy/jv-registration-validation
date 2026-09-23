package core.basesyntax.exeptions;

public class LoginValidationException extends RuntimeException {
    public LoginValidationException(String message) {
        super(message);
    }
}
