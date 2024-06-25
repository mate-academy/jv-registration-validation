package core.basesyntax.service;

public class ErrorMessage {
    public static final String ERROR_NULL_USER = "Can't pass Null";
    public static final String ERROR_ID_REQUIRED = "ID is required";
    public static final String ERROR_LOGIN_REQUIRED = "Login is required";
    public static final String ERROR_PASSWORD_REQUIRED = "Password is required";
    public static final String ERROR_AGE_REQUIRED = "Age is required";
    public static final String ERROR_USER_EXISTS = "This user already exists";
    public static final String ERROR_SHORT_LOGIN = "Login must be at least 6 characters long";
    public static final String ERROR_SHORT_PASSWORD = "Password must be at least 6 characters long";
    public static final String ERROR_UNDERAGE_USER = "User must be at least 18 years old";
    public static final String ERROR_SPACE_IN_LOGIN = "Login cannot contain spaces";
}
