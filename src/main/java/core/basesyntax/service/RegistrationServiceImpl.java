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
        checkUser(user);
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("User could not be null");
        }
    }

    void checkLogin(String login) {
        if (login == null) {
            throw new RuntimeException("Login could not be null");
        } else if (login.isEmpty() || login.isBlank()) {
            throw new RuntimeException("Login could not be empty");
        } else if (storageDao.get(login) != null) {
            throw new RuntimeException("Login already in use");
        }
    }

    void checkAge(Integer age) {
        if (age == null) {
            throw new RuntimeException("Age could not be null");
        } else if (age < MIN_AGE) {
            throw new RuntimeException("Acceptable age is 18 or greater");
        }
    }

    void checkPassword(String password) {
        if (password == null) {
            throw new RuntimeException("Password could not be null");
        } else if (password.isEmpty() || password.isBlank()) {
            throw new RuntimeException("Password could not be empty");
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password should contain six or more symbols");
        }
    }
}
