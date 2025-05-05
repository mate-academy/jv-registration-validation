package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        requireNotNull(user);
        validateUserExistence(user);
        validateUserLogin(user);
        validateUserPassword(user);
        validateUserAge(user);
        return storageDao.add(user);
    }

    private void requireNotNull(Object o) {
        if (o == null) {
            throw new RegistrationException("User cannot be null");
        }
    }

    private void validateUserExistence(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with provided login already exists");
        }
    }

    private void validateUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login cannot be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User's login should be at least 6 symbols");
        }
    }

    private void validateUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User's password should be at least 6 symbols");
        }
    }

    private void validateUserAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("User's age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User should be at least 18 years old");
        }
    }
}
