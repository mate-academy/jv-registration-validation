package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateNullValues(user);
        validateLogin(user);
        validatePassword(user);
        validAge(user);
        duplicateLogin(user);
        return storageDao.add(user);
    }

    private void validateNullValues(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }

    }

    private void validateLogin(User user) {
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Not valid login: " + user.getLogin()
                    + ". Min allowed login length is " + MIN_LENGTH);
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Not valid password: " + user.getPassword()
                    + ". Min allowed password length is " + MIN_LENGTH);
        }
    }

    private void validAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User should be at least 18 years old");
        }
    }

    private void duplicateLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
    }
}

