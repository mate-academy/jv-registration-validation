package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.exception.UserExistsException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        requireNotNull(user, "User cannot be null");
        validateUserExistence(user);
        validateUserLogin(user);
        validateUserPassword(user);
        validateUserAge(user);
        return storageDao.add(user);
    }

    private void requireNotNull(Object o, String message) {
        if (o == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private void validateUserExistence(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserExistsException("User with provided login already exists");
        }
    }

    private void validateUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new IllegalStateException("User's login cannot be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException("User's login should be at least 6 symbols");
        }
    }

    private void validateUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new IllegalStateException("User's password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("User's password should be at least 6 symbols");
        }
    }

    private void validateUserAge(User user) {
        if (user.getAge() == null) {
            throw new IllegalStateException("User's age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("User should be at least 18 years old");
        }
    }
}
