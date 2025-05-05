package core.basesyntax.model;

public class CurrentLoginIsExistsException extends RuntimeException {
    public CurrentLoginIsExistsException(String message) {
        super(message);
    }
}
