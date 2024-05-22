package core.basesyntax.service;

public class RegistrationFailedException extends RuntimeException   {
    public RegistrationFailedException(String message) {
        super(message);
    }
}
