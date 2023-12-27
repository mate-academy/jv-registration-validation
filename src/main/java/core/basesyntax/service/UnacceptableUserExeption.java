package core.basesyntax.service;

public class UnacceptableUserExeption extends RuntimeException {
    public UnacceptableUserExeption(String message) {
        super(message);
    }
}
