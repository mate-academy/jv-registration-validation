package exception;

public class RegistrationValidationException extends RuntimeException {
    public RegistrationValidationException(String message) {
        super(message);
    }
}
