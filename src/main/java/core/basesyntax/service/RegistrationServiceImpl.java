package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException(
                    "Login cannot be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    "User with this login already exists");
        }

        if (user.getLogin().length() < 6) {
            throw new RegistrationException(
                    "Login must be at least 6 characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException(
                    "Password must be at least 6 characters long");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException(
                    "User must be at least 18 years old");
        }

        storageDao.add(user);

        return user;
    }
}
