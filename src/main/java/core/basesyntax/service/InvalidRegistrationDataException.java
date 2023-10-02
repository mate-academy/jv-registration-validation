package core.basesyntax.service;

public class InvalidRegistrationDataException extends RuntimeException {
    public InvalidRegistrationDataException(String errorMessage) {
        super(errorMessage);
    }
}
