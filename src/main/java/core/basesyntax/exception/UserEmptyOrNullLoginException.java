package core.basesyntax.exception;

public class UserEmptyOrNullLoginException extends RuntimeException {

    public UserEmptyOrNullLoginException(String message) {
        super(message);
    }
}
