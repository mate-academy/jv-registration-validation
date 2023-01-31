package core.basesyntax.service.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
