package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_AMOUNT_OF_CHARACTERS = 6;
    private StorageDao storageDao;

    public RegistrationServiceImpl() {
    }

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateRegistration(user);
        return storageDao.add(user);
    }

    private void validateRegistration(User user) {
        if (user == null) {
            throw new InvalidInputDataException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidInputDataException("Login cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidInputDataException("Password cannot be null or empty");
        }
        if (user.getAge() == null) {
            throw new InvalidInputDataException("Age cannot be null");
        }

        if (user.getLogin().length() < MIN_AMOUNT_OF_CHARACTERS) {
            throw new InvalidInputDataException("Login must be at least 6 characters");
        }
        if (user.getPassword().length() < MIN_AMOUNT_OF_CHARACTERS) {
            throw new InvalidInputDataException("Password must be at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidInputDataException("User must be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputDataException("User with such login already exists");
        }
    }
}
