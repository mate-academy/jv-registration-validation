package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullValues(user);
        checkExistingUsers(user);
        checkAge(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    private void checkNullValues(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("User can't contain null values");
        }
    }

    private void checkExistingUsers(User user) {
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).equals(user)) {
            throw new RuntimeException("User already registered");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_VALID_AGE) {
            throw new RuntimeException("User's age should be at least 18 years old");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("User's age should be at least 6 characters");
        }
    }
}
