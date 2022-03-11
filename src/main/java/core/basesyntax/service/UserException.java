package core.basesyntax.service;

public class UserException extends RuntimeException {
    public UserException() {
        super("Can't add the user with same login to the list");
    }
}
