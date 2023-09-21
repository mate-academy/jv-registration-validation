package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        // Check if a user with the same login already exists
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists.");
        }

        // Check other registration criteria
        if (user.getLogin().length() < 6 || user.getPassword().length() < 6 || user.getAge() < 18) {
            throw new RegistrationException("Invalid user data.");
        }

        // Add the user to the storage if all criteria are met
        return storageDao.add(user);
    }
}
