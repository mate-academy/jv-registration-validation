package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null || user.getLogin() == null || user.getPassword()
                == null || user.getAge() == null) {
            throw new IllegalArgumentException("User or its properties cannot be null");
        }

        if (user.getLogin().length() < 6 || user.getPassword().length() < 6) {
            throw new RegistrationException("Login and password must contain "
                    + "at least 6 characters");
        }

        if (user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new RegistrationException("User with this login already exists");
        }

        return storageDao.add(user);
    }
}
