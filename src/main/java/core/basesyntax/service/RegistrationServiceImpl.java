package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        loginExists(user.getLogin());
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }
    }

    private void validateLogin(String login) {
        if (login == null || login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("login must contain more than 6 characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }
    }

    private void validateAge(Integer age) {
        if (age < MIN_AGE) {
            throw new RegistrationException("User must be over 18 years of age if:"
                    + age);
        }
    }

    private void loginExists(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("a user with such login or password already exists");
        }
    }
}
