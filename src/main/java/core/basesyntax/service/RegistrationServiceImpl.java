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

        // Validate user parameters
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new IllegalArgumentException("User must be at least 18 years old");
        }

        storageDao.add(user);
        return user;
    }
}
