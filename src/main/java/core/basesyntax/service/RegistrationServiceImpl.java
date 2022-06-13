package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final User user = new User();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        loginValidation(user.getLogin());
        passwordValidation(user.getPassword());
        ageValidation(user.getAge());
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login is already in use");
        }
        storageDao.add(user);
        return user;
    }

    private void loginValidation(String login) {
        if (login == null || login.isEmpty() || login.isBlank()) {
            throw new RuntimeException("Login is empty");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("Login is already in use");
        }
    }

    private void passwordValidation(String pasword) {
        if (pasword == null) {
            throw new RuntimeException("Password is empty");
        }
        if (pasword.length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length must be more than 6");
        }
    }

    private void ageValidation(Integer age) {
        if (age == null) {
            throw new RuntimeException("Age does not correct");
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("Age must be more 18");
        }
    }
}
