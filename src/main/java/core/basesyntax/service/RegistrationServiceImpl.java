package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ADULT_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Can't register null user!");
        }
        validateAge(user);
        validateLogin(user);
        validatePassword(user);
        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("User login shouldn't be empty!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exists!");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("User password shouldn't be empty!");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User password should at least 6 characters long!");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("User age shouldn't be empty!");
        }
        if (user.getAge() < ADULT_AGE) {
            throw new RuntimeException("User age should be at least 18 years old!");
        }
    }
}
