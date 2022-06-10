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
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("Error! Null user");
        }
    }

    void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Error! Null login");
        } else if (user.getLogin().isEmpty() || user.getLogin().isBlank()) {
            throw new RuntimeException("Error! Empty login");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Error! Login already in use");
        }
    }

    void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Error! Null age");
        } else if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Error! Age must be 18 or greater");
        }
    }

    void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Error! Password null");
        } else if (user.getPassword().isEmpty() || user.getPassword().isBlank()) {
            throw new RuntimeException("Error! Empty password");
        } else if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Error! Password less that six symbols");
        }
    }
}
