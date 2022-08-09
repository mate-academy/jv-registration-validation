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
        if (user == null) {
            throw new NullPointerException("There is no data of user");
        }
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return user;
    }

    private void checkLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RuntimeException("You should create a login!");
        }
        if (storageDao.get(login) != null && login.equals(storageDao.get(login).getLogin())) {
            throw new RuntimeException("User with this login is already registered");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("Your should enter your age!");
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("User should be over 18!");
        }
    }

    private void checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("You should create password!");
        }
        if (password.length() < MIN_PASSWORD) {
            throw new RuntimeException("Minimum password length should be 6");
        }
    }
}
