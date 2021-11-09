package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getAge() == null || user.getAge() < 18
                || user.getPassword() == null || user.getPassword().length() < 6
                || user.getLogin() == null || user.getLogin().length() < 1
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Invalid user data");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
