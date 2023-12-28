package core.basesyntax.service;

public class IncorrectUserDataExeption extends RuntimeException {
    public IncorrectUserDataExeption(String message) {
        super(message);
    }
}
