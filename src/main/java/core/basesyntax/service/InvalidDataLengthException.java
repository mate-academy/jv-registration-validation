package core.basesyntax.service;

public class InvalidDataLengthException extends RuntimeException {
    public InvalidDataLengthException(String message) { 
        super(message);
    }
}
