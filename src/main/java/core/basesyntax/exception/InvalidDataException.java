package core.basesyntax.exception;

public class InvalidDataException extends RuntimeException{
    public InvalidDataException(String message) {
        super("Error: " + message);
    }
}
