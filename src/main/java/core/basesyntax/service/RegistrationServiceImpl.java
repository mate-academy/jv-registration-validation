package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("There is no user to add");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login already exists");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("Login should be at least 6 characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password should be at least 6 characters long");
        }
        if (user.getAge() < 18 || user.getAge() > 120) {
            throw new RegistrationException("Age should be at least 18, but not more than 120");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
