package core.basesyntax.service;

public class RegistrationException extends RuntimeException {
    public UserDataInvalidException(String message) {
        super(message);
    }
}
