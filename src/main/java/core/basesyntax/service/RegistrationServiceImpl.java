package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validationPassword(user.getPassword());
        validationAge(user.getAge());
        validationLogin(user.getLogin());
        storageDao.add(user);
        return user;
    }

    private void validationLogin(String login) {
        if (login == null || login.isEmpty() || login.isBlank()) {
            throw new RuntimeException("Login is not correct");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with this login is registered");
        }
    }

    private void validationAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("Age is null");
        }
        if (age < 0) {
            throw new RuntimeException("Age must be positive number");
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("The user must be at least 18 years old");
        }
    }

    public void validationPassword(String password) {
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new RuntimeException("Password is not correct");
        }
        if (password.length() < MIN_SYMBOLS) {
            throw new RuntimeException("Password must contain at least 6 symbols");
        }
    }
}
