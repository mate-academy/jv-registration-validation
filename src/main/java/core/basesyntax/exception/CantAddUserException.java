package core.basesyntax.exception;

public class CantAddUserException extends Exception {
    public CantAddUserException(String errorMessage) {
        super(errorMessage);
    }
}
