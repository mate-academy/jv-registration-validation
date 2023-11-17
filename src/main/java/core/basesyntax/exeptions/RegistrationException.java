package core.basesyntax.exeptions;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String errorMessage) {
        super(errorMessage);
    }
}
