package core.basesyntax.model;

public class CurrentLoginIsExists extends RuntimeException {
    public CurrentLoginIsExists(String message) {
        super(message);
    }
}
