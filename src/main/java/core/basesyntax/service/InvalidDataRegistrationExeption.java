package core.basesyntax.service;

public class InvalidDataRegistrationExeption extends RuntimeException {
    public InvalidDataRegistrationExeption(String message) {
        super(message);
    }
}
