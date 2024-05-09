package core.basesyntax.exception;

public class RegistrationExceptionMessage {
    public static final String USER_CAN_NOT_BE_NULL = "Login can't be null";
    public static final String USER_EXISTS_MESSAGE = "The user with same login is existing yet";
    public static final String WRONG_LOGIN_MESSAGE =
            "The login must contain at least 6 characters, and don't start with space";
    public static final String LOGIN_IS_NOT_NULL = "Login can't be null";
    public static final String WRONG_PASSWORD_MESSAGE =
            "The password must contain at least 6 characters, and don't start with space";
    public static final String PASSWORD_IS_NOT_NULL = "Password can't be null";
    public static final String WRONG_AGE_MESSAGE = "Not valid age. Min allowed age is 18";
    public static final String AGE_IS_NOT_NULL = "Age can't be null";
}
