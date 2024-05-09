package core.basesyntax.exception;

public class RegistrationExceptionMessage {
    public static final String USER_CAN_NOT_BE_NULL = "Login can't be null";
    public static final String USER_EXISTS_MESSAGE = "The user with same login is existing yet";
    public static final String WRONG_LOGIN_MESSAGE =
            "The login must contain at least 6 characters and mustn't contain a space";
    public static final String WRONG_PASSWORD_MESSAGE =
            "The password must contain at least 6 characters and mustn't contain a space";
    public static final String WRONG_AGE_MESSAGE = "Not valid age. Min allowed age is 18";
}
