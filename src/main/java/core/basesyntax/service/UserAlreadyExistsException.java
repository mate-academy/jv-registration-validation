package core.basesyntax.service;

public class UserAlreadyExistsException extends Exception {
    private String username;

    public UserAlreadyExistsException(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User with username '" + username + "' already exists.";
    }
}
