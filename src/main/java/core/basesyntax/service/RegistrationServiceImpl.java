package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login is not valid, short login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("User password is not valid");
        }
        return storageDao.add(user);
    }
}
