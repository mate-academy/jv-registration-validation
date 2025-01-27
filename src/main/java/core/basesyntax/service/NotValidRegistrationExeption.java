package core.basesyntax.service;

public class NotValidRegistrationExeption extends RuntimeException {
    public NotValidRegistrationExeption(String message) {
        super(message);
    }
}
