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

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists");
        }

        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login too short");
        }

        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password too short");
        }

        if (user.getAge() < 18) {
            throw new RegistrationException("User is under 18");
        }

        return storageDao.add(user);
    }
}
