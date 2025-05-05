package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);

        return storageDao.add(user);
    }

    private void validateLoginUniqueness(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with such login already exists");
        }
    }

    private void validateLogin(String login) {
        checkNotNull(login, "User`s login can`t be null");

        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User`s login length should contain at least"
                    + MIN_LOGIN_LENGTH
                    + "characters, got login with " + login.length() + "characters");
        }
    }

    private void validateAge(Integer age) {
        checkNotNull(age, "User`s age can`t be null");

        if (age < MIN_USER_AGE) {
            throw new RegistrationException("User should be at least "
                    + MIN_USER_AGE
                    + " years old, got " + age + "years");
        }
    }

    private void validatePassword(String password) {
        checkNotNull(password, "User`s password can`t be null");

        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User`s password length should contain at least "
                    + MIN_PASSWORD_LENGTH
                    + " characters, got password with " + password.length() + "characters");
        }
    }

    private void checkNotNull(Object value, String errorMessage) {
        if (value == null) {
            throw new RegistrationException(errorMessage);
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null");
        }

        String login = user.getLogin();

        validateLogin(login);
        validateLoginUniqueness(login);
        validateAge(user.getAge());
        validatePassword(user.getPassword());
    }
}

