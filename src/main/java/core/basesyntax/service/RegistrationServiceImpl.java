package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public void register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login must be at least 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        storageDao.add(user);
    }
}
