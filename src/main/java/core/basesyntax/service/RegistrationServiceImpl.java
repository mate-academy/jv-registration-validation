package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RegistrationException("Invalid data");
        }
        if (user.getLogin().length() < 6 || user.getPassword().length() < 6
                ||
                user.getAge().intValue() < 18) {
            throw new RegistrationException("Invalid data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
