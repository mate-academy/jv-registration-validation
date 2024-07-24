package core.basesyntax.service;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("Login cannot be null.");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserDataException("Password cannot be null.");
        }
        if (user.getAge() == null) {
            throw new InvalidUserDataException("Age cannot be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User with this login already exists.");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidUserDataException("Login must be at least 6 characters long.");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidUserDataException("Password must be at least 6 characters long.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("User must be at least 18 years old.");
        }
        storageDao.add(user);
        return user;
    }

    public boolean isSuccesfullyRegistered(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
