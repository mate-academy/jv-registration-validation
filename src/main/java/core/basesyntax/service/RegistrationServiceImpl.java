package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl {
    private final StorageDao storageDao = new StorageDaoImpl();

    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserDataException("login cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User with such login already exists");
        }
        if (user.getAge() == null || user.getAge() < 18 || user.getAge() < 0) {
            throw new InvalidUserDataException("User must be at least 18 years old");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserDataException("Password must be at least 6 characters");
        }
        return storageDao.add(user);
    }
}
