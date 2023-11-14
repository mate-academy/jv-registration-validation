package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }

        if (user.getLogin().length() < MIN_LENGTH || user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidUserException("Login and password must be at least 6 characters");
        }

        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("User must be at least 18 years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with the same login already exists");
        }

        return storageDao.add(user);
    }
}
