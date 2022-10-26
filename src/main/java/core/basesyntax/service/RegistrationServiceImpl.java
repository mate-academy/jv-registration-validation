package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_VALUE_OF_YEAR = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNull(user);
        checkAge(user);
        checkPassword(user);
        checkExistingUser(user);
        return storageDao.add(user);
    }

    private User checkNull(User user) {
        if (user == null) {
            throw new RuntimeException("Invalid user. User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("login cannot be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age cannot be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password cannot be null");
        }
        return user;
    }

    private User checkAge(User user) {
        if (user.getAge() < 0) {
            throw new RuntimeException("Age cannot be less than 0");
        }
        if (user.getAge() < MIN_VALID_VALUE_OF_YEAR) {
            throw new RuntimeException("User is too yang");
        }
        return user;
    }

    private User checkPassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Password is too short");
        }
        return user;
    }

    private User checkExistingUser(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already registered");
        }
        return user;
    }
}
