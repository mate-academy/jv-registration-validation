package core.basesyntax.service.exceptions;

public class UserTooYoungException extends RuntimeException {
    public UserTooYoungException(String message) {
        super(message);
    }
}
