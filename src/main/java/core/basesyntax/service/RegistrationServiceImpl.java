package core.basesyntax.service;

import core.basesyntax.custom.exception.UserValidationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNullValues(user);
        loginValid(user);
        userNotExists(user);
        ageValid(user);
        passwordValid(user);
        return storageDao.add(user);
    }

    private void checkForNullValues(User user) {
        if (user == null) {
            throw new UserValidationException("User can't be null");
        } else if (user.getLogin() == null) {
            throw new UserValidationException("Login can't be null");
        } else if (user.getPassword() == null) {
            throw new UserValidationException("Password can't be null");
        } else if (user.getAge() == null) {
            throw new UserValidationException("Age can't be null");
        }
    }

    private void loginValid(User user) {
        if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            throw new UserValidationException(
                    "Can't be empty or contain whitespace");
        }
    }

    private void userNotExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserValidationException("Login is already registered");
        }
    }

    private void ageValid(User user) {
        if (user.getAge() < USER_MIN_AGE) {
            throw new UserValidationException(
                    "Must meet the minimum age requirement");
        }
    }

    private void passwordValid(User user) {
        if (user.getPassword().isEmpty() || user.getPassword().contains(" ")
                || user.getPassword().length() < USER_PASSWORD_MIN_LENGTH) {
            throw new UserValidationException(
                    "Must be at least 6 characters without whitespace");
        }
    }
}
