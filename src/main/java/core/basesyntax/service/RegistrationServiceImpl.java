package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        validateUserNotNull(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        validateUniqueLogin(user.getLogin());
    }

    private void validateUserNotNull(User user) {
        if (user == null) {
            throw new InvalidUserException("Object user can not be null");
        }
    }

    private void validateLogin(String login) {
        if (login == null || login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException("Invalid login");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("Invalid password");
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new InvalidUserException("Invalid age");
        }
    }

    private void validateUniqueLogin(String login) {
        if (storageDao.get(login) != null) {
            throw new InvalidUserException("User with such login already exists");
        }
    }
}

