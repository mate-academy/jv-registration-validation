package core.basesyntax.service;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String masage) {
        super(masage);
    }
}
