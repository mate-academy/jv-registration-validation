package core.basesyntax.exception;

public class WrongDataException extends IllegalArgumentException {
    public WrongDataException() {
        super();
    }

    public WrongDataException(String message) {
        super(message);
    }
}
