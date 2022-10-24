package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verifyAge(user.getAge());
        verifyLogin(user.getLogin());
        verifyPassword(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private void verifyAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("The age cannot be empty field");
        }
        if (age < 0) {
            throw new RuntimeException("Age cannot be negative");
        }
        if (age < MINIMUM_AGE) {
            throw new RuntimeException("Your age should be 18 and higher");
        }
    }

    private void verifyLogin(String login) {
        if (login == null
                || login.isEmpty()
                || login.isBlank()) {
            throw new RuntimeException("The login cannot be empty field");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with such login already exists");
        }
    }

    private void verifyPassword(String password) {
        if (password == null
                || password.isBlank()
                || password.isEmpty()) {
            throw new RuntimeException("Password cannot be empty field");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password should be at least 6 characters long");
        }
    }
}
