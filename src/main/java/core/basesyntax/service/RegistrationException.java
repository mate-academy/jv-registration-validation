package core.basesyntax.service;

public class RegistrationException extends RuntimeException {
    private String message;

    public RegistrationException(String message) {
        this.message = message;
    }
}
