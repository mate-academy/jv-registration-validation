package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getPassword().length() < 6
                || user.getAge() < 18 || user.getLogin() == null) {
            throw new RuntimeException();
        }

        if (storageDao.get(user.getLogin()) == null) {
            storageDao.add(user);
            return user;
        } else {
            throw new RuntimeException();
        }
    }
}
