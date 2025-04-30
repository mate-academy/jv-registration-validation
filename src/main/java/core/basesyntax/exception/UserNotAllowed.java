package core.basesyntax.exception;

public class UserNotAllowed extends RuntimeException {
    public UserNotAllowed(String message) {
        super(message);
    }

    public UserNotAllowed() {
        super();
    }

}
