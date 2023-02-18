package core.basesyntax.service;

public class RegisterNotValidException extends RuntimeException {
    public RegisterNotValidException(String message) {
        super(message);
    }
}
