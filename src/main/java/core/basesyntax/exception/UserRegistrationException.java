package core.basesyntax.exception;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String massage) {
        super(massage);
    }
}
