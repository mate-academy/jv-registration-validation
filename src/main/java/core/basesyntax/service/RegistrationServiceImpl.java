package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserLogin(user.getLogin());
        checkUserPassword(user.getPassword());
        checkUserAge(user.getAge());
        storageDao.add(user);
        return user;
    }

    public void checkUserLogin(String login) {
        if (login == null || login.isEmpty() || login.isBlank()) {
            throw new RuntimeException("User login is null!");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with this login exists in storage!");
        }
    }

    public void checkUserPassword(String password) {
        if (password == null || password.isEmpty() || password.isBlank()) {
            throw new RuntimeException("User password is null!");
        }
        if (password.length() < PASSWORD_LENGTH) {
            throw new RuntimeException("User password is less than 6 characters!");
        }
    }

    public void checkUserAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("User age is null!");
        }
        if (age < 0) {
            throw new RuntimeException("User age is less than zero!");
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("User age is less than 18!");
        }
    }
}
