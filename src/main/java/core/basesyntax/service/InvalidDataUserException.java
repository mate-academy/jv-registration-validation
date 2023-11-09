package core.basesyntax.service;

public class InvalidDataUserException extends RuntimeException {

    public InvalidDataUserException(String message) {
        super(message);
    }
}
