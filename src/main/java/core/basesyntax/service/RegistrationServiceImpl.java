package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.except.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        Storage storage = new Storage();
        userNotNullVerify(user);
        loginVerify(user, storageDao);
        ageVerify(user);
        passwordVerify(user);
        return storageDao.add(user);
    }

    private static void userNotNullVerify(User user) {
        if (user == null) {
            throw new InvalidDataException("User can`t be null");
        }
    }

    private static void loginVerify(User user, StorageDao storageDao) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with the same login already exists");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
    }

    private static void passwordVerify(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password can't be less than 6");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
    }

    private static void ageVerify(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Null age is not valid");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Not valid age");
        }
    }
}
