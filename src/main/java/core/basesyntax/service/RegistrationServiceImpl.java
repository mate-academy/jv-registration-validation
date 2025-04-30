package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_PASSWORD_MIN_LENGTH = 6;
    private static final String LOGIN_PASSWORD_PATTERN = ".{6,}";
    private static final int MIN_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserRegistrationException(nullErrorMessage("User"));
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new UserRegistrationException(nullErrorMessage("Login"));
        }
        if (!login.matches(LOGIN_PASSWORD_PATTERN)) {
            throw new UserRegistrationException(loginPasswordErrorMessage("Login",
                    LOGIN_PASSWORD_MIN_LENGTH));
        }
        if (storageDao.get(login) != null) {
            throw new UserRegistrationException("User with this login already exists");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new UserRegistrationException(nullErrorMessage("Password"));
        }
        if (!password.matches(LOGIN_PASSWORD_PATTERN)) {
            throw new UserRegistrationException(loginPasswordErrorMessage("Password",
                    LOGIN_PASSWORD_MIN_LENGTH));
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new UserRegistrationException(nullErrorMessage("Age"));
        }
        if (age < MIN_VALID_AGE) {
            throw new UserRegistrationException("Minimum age to register " + MIN_VALID_AGE);
        }
    }

    private String nullErrorMessage(String dataName) {
        return dataName + " can't be null";
    }

    private String loginPasswordErrorMessage(String data, int length) {
        return String.format("%s must contain at least %d characters", data, length);
    }
}
