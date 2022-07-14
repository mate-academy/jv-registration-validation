package core.basesyntax.exceptions;

public class DuplicateAnExistingLoginException extends RuntimeException {
    public DuplicateAnExistingLoginException(String message) {
        super(message);
    }
}
