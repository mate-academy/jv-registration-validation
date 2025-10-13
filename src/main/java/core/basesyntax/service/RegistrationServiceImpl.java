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
        if (user.getLogin() == null) {
            throw new ValidationException("“Login cannot be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new ValidationException("Login must be at least MIN_LENGTH characters");
        }
        if (user.getPassword() == null) {
            throw new ValidationException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new ValidationException("Password must be at least MIN_LENGTH characters");
        }
        if (user.getAge() == null) {
            throw new ValidationException("Age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("Age must be at least 18");
        }
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new ValidationException("User with this login already exists");
        }
    }
}
