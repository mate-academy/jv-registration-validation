package core.basesyntax.service;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class UserValidatorImpl implements UserValidator {
    private static final String USER_CANNOT_BE_NULL = "User cannot be null";
    private static final String LOGIN_TOO_SHORT = "User's login must have at least 6 characters";
    private static final String LOGIN_CANNOT_BE_NULL = "User login cannot be null";
    private static final String PASSWORD_TOO_SHORT = "User's password must have at least 6 characters";
    private static final String PASSWORD_CANNOT_BE_NULL = "User password cannot be null";
    private static final String AGE_TOO_LOW = "User's age must be at least 18 years old";
    private static final String AGE_CANNOT_BE_NULL = "User age cannot be null";
    private static final String SPACE = " ";
    private static final String PASSWORD_CANNOT_CONTAIN_SPACES = "User login cannot contain spaces. Login: ";
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;

    @Override
    public void validate(User user) throws ValidationException {
        if (user == null) {
            throw new ValidationException(USER_CANNOT_BE_NULL);
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
    }

    @Override
    public void validateLogin(String login) throws ValidationException {
        if (login == null) {
            throw new ValidationException(LOGIN_CANNOT_BE_NULL);
        }
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new ValidationException(LOGIN_TOO_SHORT);
        }
        if (login.contains(SPACE)) {
            throw new ValidationException(PASSWORD_CANNOT_CONTAIN_SPACES + login);
        }
    }

    @Override
    public void validatePassword(String password) throws ValidationException {
        if (password == null) {
            throw new ValidationException(PASSWORD_CANNOT_BE_NULL);
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new ValidationException(PASSWORD_TOO_SHORT);
        }
    }

    @Override
    public void validateAge(Integer age) throws ValidationException {
        if (age == null) {
            throw new ValidationException(AGE_CANNOT_BE_NULL);
        }
        if (age < USER_MIN_AGE) {
            throw new ValidationException(AGE_TOO_LOW);
        }
    }
}
