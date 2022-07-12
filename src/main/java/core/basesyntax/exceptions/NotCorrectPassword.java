package core.basesyntax.exceptions;

public class NotCorrectPassword extends RuntimeException {
    public NotCorrectPassword(String message) {
        super(message);
    }
}
