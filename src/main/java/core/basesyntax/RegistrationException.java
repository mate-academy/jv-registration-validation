package core.basesyntax;

public class RegistrationException extends RuntimeException {
    public static final String USER_NULL_MSG = "User can't be null";
    public static final String INCORRECT_LOGIN_MSG = "User login is incorrect";
    public static final String INCORRECT_PASSWORD_MSG = "User password is incorrect";
    public static final String INCORRECT_AGE_MSG = "User age is incorrect";
    public static final String ALREADY_RGSTR_USER_MSG = "User already registered with such login";

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException() {
    }
}
