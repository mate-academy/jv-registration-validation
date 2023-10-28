package core.basesyntax.service;

import core.basesyntax.IllegalUserDataExeption;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        checkUserNotExists(user.getLogin());
        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null || login.length() < MIN_LOGIN_LENGTH) {
            throw new IllegalUserDataExeption("Login " + login + " not valid");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalUserDataExeption("This password not valid");
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new IllegalUserDataExeption("Age " + age + " not valid");
        }
    }

    private void checkUserNotExists(String login) {
        if (storageDao.get(login) != null) {
            throw new IllegalUserDataExeption("User with login " + login + " already exists");
        }
    }
}
