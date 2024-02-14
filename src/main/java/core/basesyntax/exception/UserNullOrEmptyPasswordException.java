package core.basesyntax.exception;

public class UserNullOrEmptyPasswordException extends RuntimeException {

    public UserNullOrEmptyPasswordException(String message) {
        super(message);
    }
}
