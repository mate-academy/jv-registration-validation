package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationFailedException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null || user.getLogin() == null || user.getPassword() == null) {
            throw new RegistrationFailedException("Null data is not allowed");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationFailedException("User's login must be at least 6 characters");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationFailedException("User's password must be at least 6 characters");
        }
        if (user.getAge() < 18) {
            throw new RegistrationFailedException("User's age must be at least 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailedException("User with this login already exists");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
