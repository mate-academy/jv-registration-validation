package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final byte MIN_LOG_PASSWORD_LENGTH = 6;
    private static final byte MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullUser(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        storageDao.add(user);
        return user;
    }

    private void checkNullUser(User user) {
        if (user.equals(null)) {
            throw new RegistrationException("User cannot be null");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be at least 18 years old");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null
                || user.getLogin().length() < MIN_LOG_PASSWORD_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with the same login already exists");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_LOG_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
    }
}
