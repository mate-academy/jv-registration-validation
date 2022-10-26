package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isUserValid(user)) {
            storageDao.add(user);
        }
        return user;
    }

    public boolean isUserValid(User user) {
        if (user == null) {
            throw new RuntimeException("User can’t be null");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return true;
    }

    private void checkLogin(String login) {
        if (login == null || storageDao.get(login) != null) {
            throw new RuntimeException("Empty login or user with same login is exist");
        }
    }

    private void checkPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is invalid");
        }
    }

    private void checkAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new RuntimeException("Your age must be below " + MIN_AGE);
        }
    }
}
