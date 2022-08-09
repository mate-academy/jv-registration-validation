package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
        public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User could not be null!");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login could not be null! " + user);
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age could not be null! " + user);
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password could not be null! " + user);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exists!");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("User is to be at least 18 years!");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("User`s password is to be at least 6 characters!");
        }
        return storageDao.add(user);
    }
}
