package core.basesyntax.exeptions;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String msg) {
        super(msg);
    }
}
