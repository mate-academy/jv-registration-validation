package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int CHARACTERS_LIMIT = 6;
    private static final int APPROPRIATE_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        nullChecking(user);
        loginChecking(user);

        if (user.getPassword().length() < CHARACTERS_LIMIT) {
            throw new RegistrationException("Password should be at least 6 characters");
        }

        ageChecking(user);

        storageDao.add(user);
        return user;
    }

    private void nullChecking(User user) {
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

    private void loginChecking(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user with such login already exists");
        }
        if (user.getLogin().length() < CHARACTERS_LIMIT) {
            throw new RegistrationException("Login should be at least 6 characters");
        }
    }

    private void ageChecking(User user) {
        if (user.getAge() <= 0) {
            throw new RegistrationException("Age should be a positive number");
        }
        if (user.getAge() < APPROPRIATE_AGE) {
            throw new RegistrationException("You are under " + APPROPRIATE_AGE + " years old");
        }
    }
}
