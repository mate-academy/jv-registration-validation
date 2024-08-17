package core.basesyntax.service;

public class UserDataInvalidException extends RuntimeException {
    private String message;

    public UserDataInvalidException(String message) {
        this.message = message;
    }
}
