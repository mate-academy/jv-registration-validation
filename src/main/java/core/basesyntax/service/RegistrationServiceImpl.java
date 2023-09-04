package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_LENGTH = 6;
    private static final int MIN_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return saveUser(user);
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null.");
        }
        if (user.getLogin().isBlank() || user.getLogin().length() < 6) {
            throw new RegistrationException(
                    "User login should have at least 6 non-whitespace characters");
        }

    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null.");
        }
        if (user.getPassword().isBlank() || user.getPassword().length() < MIN_VALID_LENGTH) {
            throw new RegistrationException(
                    "User password should have at least 6 non-whitespace characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_VALID_AGE) {
            throw new RegistrationException("User age should be 18 or more");
        }
    }

    private User saveUser(User user) {
        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
        } else {
            throw new RegistrationException(String.format(
                    "User with login %s already exists in the storage", user.getLogin()));
        }
        return user;
    }
}
