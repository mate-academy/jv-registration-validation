package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null
                || user.getAge() < 18 || user.getPassword().length() < 6
                || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException();
        }
        return storageDao.add(user);
    }
}
