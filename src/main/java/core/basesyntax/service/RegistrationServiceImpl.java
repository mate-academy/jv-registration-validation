package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Check if user already exists in the database
        for (User existingUser : storageDao.getAll()) {
            if (existingUser.getLogin().equals(user.getLogin())) {
                throw new IllegalArgumentException("User with this login already exists");
            }
        }

        // Validate user parameters
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().length() < 6) {
            throw new IllegalArgumentException("Login cannot be null, "
                    + "empty or less than 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()
                || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password cannot be null, "
                    + "empty or less than 6 characters");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new IllegalArgumentException("User must be at "
                    + "least 18 years old");
        }

        storageDao.add(user);
        return user;
    }
}
