package core.basesyntax.service;

public class InvalidRegistrationServiceException extends RuntimeException {
    public InvalidRegistrationServiceException(String message) {
        super(message);
    }
}
