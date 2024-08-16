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
            throw new UserDataInvalidException("User with such login already exists");
        }
    }

    private void validateLogin(String login) {
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new UserDataInvalidException("User`s login length should contain at least"
                    + MIN_LOGIN_LENGTH
                    + "characters, got login with " + login.length() + "characters");
        }
    }

    private void validateAge(Integer age) {
        if (age < MIN_USER_AGE) {
            throw new UserDataInvalidException("User should be at least "
                    + MIN_USER_AGE
                    + " years old, got " + age + "years");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new UserDataInvalidException("User`s password length should contain at least "
                    + MIN_PASSWORD_LENGTH
                    + " characters, got password with " + password.length() + "characters");
        }
    }

    private void checkNotNull(Object value, String errorMessage) {
        if (value == null) {
            throw new UserDataInvalidException(errorMessage);
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new UserDataInvalidException("User can`t be null");
        }

        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        checkNotNull(login, "User`s login can`t be null");
        checkNotNull(password, "User`s password can`t be null");
        checkNotNull(age, "User`s age can`t be null");

        validateAge(age);
        validateLogin(login);
        validateLoginUniqueness(login);
        validatePassword(password);
    }
}


