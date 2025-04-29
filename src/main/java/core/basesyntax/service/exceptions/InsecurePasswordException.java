package core.basesyntax.service.exceptions;

public class InsecurePasswordException extends RuntimeException {
    public InsecurePasswordException(String message) {
        super(message);
    }
}
