package core.basesyntax.service;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super("This user can't be register.");
    }
}
