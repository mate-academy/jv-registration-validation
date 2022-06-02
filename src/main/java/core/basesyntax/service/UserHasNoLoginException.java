package core.basesyntax.service;

public class UserHasNoLoginException extends RuntimeException {

    UserHasNoLoginException(String message) {
        super(message);
    }
}
