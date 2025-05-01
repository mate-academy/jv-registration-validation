package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new ValidationException("Age can't be null");
        }
        if (!isValidUserCredentials(user)) {
            throw new ValidationException("Login and password should contain"
                    + " at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("User should be at least 18 years old");
        }
        if (isLoginAlreadyUsed(user)) {
            throw new ValidationException("This login is already in use");
        }
        return storageDao.add(user);
    }

    private boolean isValidUserCredentials(User user) {
        if (user.getLogin().length() < MIN_LENGTH || user.getPassword().length() < MIN_LENGTH) {
            return false;
        }
        return true;
    }

    private boolean isLoginAlreadyUsed(User user) {
        User userFromStorage = storageDao.get(user.getLogin());
        if (user.getLogin() != null && userFromStorage != null
                && user.getLogin().equals(userFromStorage.getLogin())) {
            return true;
        }
        return false;
    }
}

