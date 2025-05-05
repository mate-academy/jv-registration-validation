package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final int minLoginLength = 6;
    private final int minPasswordLength = 6;
    private final int minUserAge = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exist");
        }
        if (user.getLogin().length() < minLoginLength) {
            throw new RegistrationException("Login must be at least 6 characters long");
        }
        if (user.getPassword().length() < minPasswordLength) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }
        if (user.getAge() == null || user.getAge() < minUserAge) {
            throw new RegistrationException(
                    "You must be at least 18 years old to register account"
            );
        }
        storageDao.add(user);
        return user;
    }
}
