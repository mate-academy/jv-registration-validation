package core.basesyntax.exeptions;

public class UserIsNullException extends RuntimeException {
    public UserIsNullException(String message) {
        super(message);
    }
}
