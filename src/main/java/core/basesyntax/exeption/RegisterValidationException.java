package core.basesyntax.exeption;

public class RegisterValidationException extends RuntimeException {
    public RegisterValidationException(String message) {
        super(message);
    }
}
