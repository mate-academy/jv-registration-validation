package core.basesyntax;

public class RegistrationIsFailedException extends RuntimeException {
    public RegistrationIsFailedException(String message) {
        super(message);
    }
}
