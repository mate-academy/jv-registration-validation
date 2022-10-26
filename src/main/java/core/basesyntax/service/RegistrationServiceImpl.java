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
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        loginChecking(user);
        passwordChecking(user);
        ageChecking(user);
        storageDao.add(user);
        return user;
    }

    private void loginChecking(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Invalid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login has already existed");
        }
    }

    private void passwordChecking(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Invalid password");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be at least 6 characters");
        }
    }

    private void ageChecking(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be empty");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Invalid age");
        }
    }
}
