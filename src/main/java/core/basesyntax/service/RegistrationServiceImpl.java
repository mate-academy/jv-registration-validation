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
            throw new RegistrationException(" User can't be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("user's login must be at least 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("user's password must be at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new RegistrationException("user's age must at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exist");
        }

        return storageDao.add(user);
    }
}
