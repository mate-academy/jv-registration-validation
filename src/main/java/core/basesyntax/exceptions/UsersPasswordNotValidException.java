package core.basesyntax.exceptions;

public class UsersPasswordNotValidException extends RuntimeException {
    public UsersPasswordNotValidException(String message) {
        super(message);
    }
}
