package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getId() == null
                || user.getAge() == null || user.getPassword() == null
                || user.equals(storageDao.get(user.getLogin()))
                || user.getAge() < 18 || user.getPassword().length() < 6) {
            throw new RuntimeException("Cannot register user");
        }
        return storageDao.add(user);
    }
}
