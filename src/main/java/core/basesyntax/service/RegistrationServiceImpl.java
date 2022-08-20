package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null!");
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("All fields User must be not empty!");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("You are very young for this ;)");
        }
        if (user.getPassword().trim().length() < 6) {
            throw new RegistrationException("Your password is small. "
                    + " The password must be at least 6 characters long.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("A user with the same login is already registered.");
        }
        return storageDao.add(user);
    }
}
