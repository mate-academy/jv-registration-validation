package core.basesyntax.service;

public class InvalidUserException extends Exception{
    public InvalidUserException(String message) {
        super(message);
    }
}
