package core.basesyntax.exception;

public class CreateUserException extends IllegalArgumentException {
    public CreateUserException() {
        super();
    }

    public CreateUserException(String s) {
        super(s);
    }
}
