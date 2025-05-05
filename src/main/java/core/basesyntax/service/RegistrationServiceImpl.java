package core.basesyntax.service;

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
            throw new RegistrationException("Login must be more than 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login cannot repeat");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Login must be more than 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationException("Age cannot be under 18");
        }

        return storageDao.add(user);
    }
}
