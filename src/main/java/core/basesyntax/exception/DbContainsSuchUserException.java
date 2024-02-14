package core.basesyntax.exception;

public class DbContainsSuchUserException extends RuntimeException {

    public DbContainsSuchUserException(String message) {
        super(message);
    }
}
