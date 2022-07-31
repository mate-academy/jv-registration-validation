package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null
        || user.getAge() < 18
        || user.getPassword().length() < 6) {
            throw new RuntimeException();
        }
        storageDao.add(user);
        return user;

    }
}
