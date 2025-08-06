package core.basesyntax.service;

public class ExistingLoginException extends RuntimeException {

    public ExistingLoginException(String message) {
        super(message);
    }
}
