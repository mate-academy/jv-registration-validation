package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH) {
            throw new ValidationException("Login must be at least 6 characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH) {
            throw new ValidationException("Password must be at least 6 characters long");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new ValidationException("Age must be at least 18");
        }
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new ValidationException("User with this login already exists");
        }
    }
}
