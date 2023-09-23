package core.basesyntax.service;

public class RegistrationError extends RuntimeException {
    public RegistrationError(String massage) {
        super(massage);
    }
}
