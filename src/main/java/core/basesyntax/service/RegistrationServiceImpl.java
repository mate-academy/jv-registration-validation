package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verifyUserNotNull(user);
        verifyLogin(user);
        verifyAge(user);
        verifyPassword(user);
        return storageDao.add(user);
    }

    private static void verifyUserNotNull(User user) {
        if (user == null) {
            throw new InvalidDataException("User can`t be null");
        }
    }

    private static void verifyLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with the same login already exists");

        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
    }

    private static void verifyPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password can't be less than " + MIN_PASSWORD_LENGTH);
        }
    }

    private static void verifyAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Null age is not valid");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Not valid age."
                    + " Age is less than minimum required age: " + MIN_AGE);
        }
    }
}
