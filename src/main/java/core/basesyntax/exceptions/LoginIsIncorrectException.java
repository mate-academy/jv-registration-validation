package core.basesyntax.exceptions;

public class LoginIsIncorrectException extends RuntimeException {
    public LoginIsIncorrectException(String message) {
        super(message);
    }
}
