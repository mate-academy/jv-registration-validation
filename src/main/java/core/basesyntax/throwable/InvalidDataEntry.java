package core.basesyntax.throwable;

public class InvalidDataEntry extends RuntimeException {
    public InvalidDataEntry(String message) {
        super(message);
    }
}
