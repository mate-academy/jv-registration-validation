package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < 18) {
            throw new RuntimeException("You must be 18 years old to register");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("this login is already registered, use another login");
        }
        if (user.getPassword().length() < 5) {
            throw new RuntimeException("password must be at least 6  character");
        }
        return storageDao.add(user);
    }
}
