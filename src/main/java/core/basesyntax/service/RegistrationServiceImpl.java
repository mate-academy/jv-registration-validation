package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void register(User user) {
        // Validate the user input
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidUserException("Login must be at least 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserException("Password must be at least 6 characters");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidUserException("User must be at least 18 years old");
        }

        // Check if the user with the same login already exists
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with this login already exists");
        }

        // If all validations pass, add the user to the storage
        storageDao.add(user);
    }
}
