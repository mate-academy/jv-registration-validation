package core.basesyntax.exceptions;

public class PasswordIsIncorrectException extends RuntimeException {
    public PasswordIsIncorrectException(String message) {
        super(message);
    }
}
