package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("Your login should be unique");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Your password should have at least 6 characters");
        }
    }

    private void validateAge(int age) {
        if (age == 0) {
            throw new RuntimeException("Age can't be 0");
        }
        if (age < MIN_AGE) {
            throw new RuntimeException("Your age should be at least 18");
        }
    }
}
