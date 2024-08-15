package core.basesyntax.exceptions;

public class RegistrationFailed extends RuntimeException {
    public RegistrationFailed(String message) {
        super(message);
    }
}
