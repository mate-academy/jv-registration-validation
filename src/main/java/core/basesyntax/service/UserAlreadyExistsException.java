package core.basesyntax.service;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super("User with username '" + username + "' already exists.");
    }
}
