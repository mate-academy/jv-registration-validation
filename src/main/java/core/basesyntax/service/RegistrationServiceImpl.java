package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_CREDENTIAL_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserNotNull(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        validateUserUniqueness(user);
        return storageDao.add(user);
    }

    private void validateUserNotNull(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getLogin().length() < MIN_CREDENTIAL_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_CREDENTIAL_LENGTH + " characters long");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_CREDENTIAL_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_CREDENTIAL_LENGTH + " characters long");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Age cannot be negative");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be at least " + MIN_AGE + " years old");
        }
    }

    private void validateUserUniqueness(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
    }
}
