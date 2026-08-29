package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_AND_PASS_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < LOGIN_AND_PASS_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists");
        }
        if (user.getPassword() == null || user.getPassword().length() < LOGIN_AND_PASS_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be at least 18");
        }

        User added = storageDao.add(user);
        return added;
    }
}
