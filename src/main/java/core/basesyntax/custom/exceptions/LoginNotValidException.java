package core.basesyntax.custom.exceptions;

public class LoginNotValidException extends RuntimeException {
    public LoginNotValidException(String message) {
        super(message);
    }
}
