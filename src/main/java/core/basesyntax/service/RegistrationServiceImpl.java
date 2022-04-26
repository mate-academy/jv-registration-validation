package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < 18) {
            throw new RuntimeException("Invalid age. The user must be at least 18 years old");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password length must be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login is already used. Create new login");
        }
        return storageDao.add(user);
    }
}
