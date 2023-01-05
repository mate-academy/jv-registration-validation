package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class ValidationServiceImpl implements ValidationService {
    private static final int ADULT_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EMPTY_LOGIN_MESSAGE = "Login is empty";
    private static final String EMPTY_PASSWORD_MESSAGE = "Password is empty";
    private static final String EMPTY_AGE_MESSAGE = "Age is empty";
    private static final String MIN_LENGTH_PASSWORD_MESSAGE
            = "Password needs 6 characters at least";
    private static final String MIN_LIMIT_AGE_MESSAGE = "You are so little";
    private static final String EMPTY_USER_MESSAGE = "User is empty";
    private final StorageDao storageDao = new StorageDaoImpl();

    private void notNullInput(Object input, String message) {
        if (input == null) {
            throw new ValidationException(message);
        }
    }

    private void inputMinLimit(int limit, Integer input, String message) {
        if (input < limit) {
            throw new ValidationException(message);
        }
    }

    private void existsLogin(String login) {
        if (storageDao.get(login) != null) {
            throw new ValidationException("Login already exists");
        }
    }

    @Override
    public void validateUser(User user) {
        notNullInput(user, EMPTY_USER_MESSAGE);
        String password = user.getPassword();
        String login = user.getLogin();
        Integer age = user.getAge();
        notNullInput(login, EMPTY_LOGIN_MESSAGE);
        notNullInput(password, EMPTY_PASSWORD_MESSAGE);
        notNullInput(age, EMPTY_AGE_MESSAGE);
        existsLogin(login);
        inputMinLimit(MIN_PASSWORD_LENGTH, password.length(), MIN_LENGTH_PASSWORD_MESSAGE);
        inputMinLimit(ADULT_AGE, age, MIN_LIMIT_AGE_MESSAGE);
    }
}
