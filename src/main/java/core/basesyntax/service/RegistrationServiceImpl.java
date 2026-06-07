package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login is invalid");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password is invalid");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("Age is invalid");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }

        return storageDao.add(user);
    }
}
