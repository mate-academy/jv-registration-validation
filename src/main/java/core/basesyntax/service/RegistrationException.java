package core.basesyntax.service;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String message) {
        super("User not registered. " + message + " Please, check input data and try again.");
    }
}
