package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login must contain 6 or more symbols");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password must contain 6 or more symbols");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        return storageDao.add(user);
    }
}
