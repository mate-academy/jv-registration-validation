package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
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
            throw new InvalidUserException("User cannot be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserException("Login cannot be null");
        }
        if (user.getLogin().length() < MIN_CREDENTIAL_LENGTH) {
            throw new InvalidUserException("Login must be at least "
                    + MIN_CREDENTIAL_LENGTH + " characters long");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidUserException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_CREDENTIAL_LENGTH) {
            throw new InvalidUserException("Password must be at least "
                    + MIN_CREDENTIAL_LENGTH + " characters long");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidUserException("Age cannot be null");
        }
        if (user.getAge() < 0) {
            throw new InvalidUserException("Age cannot be negative");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("User must be at least " + MIN_AGE + " years old");
        }
    }

    private void validateUserUniqueness(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with this login already exists");
        }
    }
}
