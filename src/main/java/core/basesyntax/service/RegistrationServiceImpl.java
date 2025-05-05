package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getAge() == null
                || user.getPassword() == null) {
            throw new NullPointerException("Incorrect data!");
        }
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login.isEmpty()) {
            throw new RuntimeException("User should enter a login");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with this login is already registered");
        }
    }

    private void checkAge(Integer age) {
        if (age < MIN_AGE) {
            throw new RuntimeException("User should be over 18!");
        }
    }

    private void checkPassword(String password) {
        if (password.isEmpty()) {
            throw new RuntimeException("User should create a password");
        }
        if (password.length() < MIN_PASSWORD) {
            throw new RuntimeException("Minimum password length should be 6");
        }
    }
}
