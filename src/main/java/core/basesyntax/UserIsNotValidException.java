package core.basesyntax;

public class UserIsNotValidException extends RuntimeException {
    public UserIsNotValidException(String message) {
        super(message);
    }
}
