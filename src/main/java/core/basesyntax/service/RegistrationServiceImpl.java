package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationException("Login must contain at least 6 characters");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationException("Password must contain at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("User must be at least 18 years old");
        }
        return storageDao.add(user);
    }
}
