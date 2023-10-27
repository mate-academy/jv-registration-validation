package core.basesyntax.service;

import core.basesyntax.IllegalUserDataExeption;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < 6
                || user.getPassword() == null || user.getPassword().length() < 6
                || user.getAge() == null || user.getAge() < 18
                || storageDao.get(user.getLogin()) != null) {
            throw new IllegalUserDataExeption("User data not valid");
        }
        return storageDao.add(user);
    }
}
