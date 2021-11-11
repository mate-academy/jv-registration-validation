package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Can`t register user, invalid data");
        }

        if (storageDao.get(user.getLogin()) == null
                && user.getPassword().length() >= 6
                && user.getAge() >= 18
                && user.getLogin().length() >= 1) {
            return storageDao.add(user);
        } else {
            throw new RuntimeException("Can't register user, invalid data");
        }
    }
}
