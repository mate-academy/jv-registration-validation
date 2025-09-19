package core.basesyntax.exeptions;

public class PasswordIsNullException extends RuntimeException {
    public PasswordIsNullException(String message) {
        super(message);
    }
}
