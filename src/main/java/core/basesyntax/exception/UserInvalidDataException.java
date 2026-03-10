package core.basesyntax.exception;

public class UserInvalidDataException extends RuntimeException {
    public UserInvalidDataException(String message) {
        super("Check your data: " + message);
    }
}
