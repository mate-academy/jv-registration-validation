package core.basesyntax.service;

public class InvalidUserExeption extends RuntimeException {
    public InvalidUserExeption(String message) {
        super(message);
    }
}
