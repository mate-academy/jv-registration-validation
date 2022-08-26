package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int ZERO = 0;
    private static final int MIN_VALID_AGE = 18;
    private static final int MAX_VALID_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNotNull(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }


    private void checkUserNotNull(User user) {
        if (user == null) {
            throw new RuntimeException("Can't register user. Current user: null");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RuntimeException("Can't register user with login: null");
        }
        if (login.isEmpty() || login.isBlank()) {
            throw new RuntimeException("Login is empty");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with login - " + login + " exists");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RuntimeException("Can't register user with password: null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length must be at least 6 characters. "
                    + "Current password length: " + password.length());
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("Can't register user with age: null");
        }
        if (age <= ZERO || age > MAX_VALID_AGE) {
            throw new RuntimeException("Invalid age. Value can't be: " + age);
        }
        if (age < MIN_VALID_AGE) {
            throw new RuntimeException("User must be at least 18 years old. Current age: " + age);
        }
    }
}
