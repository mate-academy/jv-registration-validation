package core.basesyntax.exeptions;

public class LoginIsNullException extends RuntimeException {
    public LoginIsNullException(String message) {
        super(message);
    }
}
