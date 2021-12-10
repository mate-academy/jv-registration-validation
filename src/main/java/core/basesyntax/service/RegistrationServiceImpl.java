package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == 0) {
            throw new RuntimeException("Empy one from requested position");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login have existed");
        }

        if (user.getAge() < 18) {
            throw new RuntimeException("User age have to more or equal 18");
        }

        if (user.getAge() > 135) {
            throw new RuntimeException("Your age unreal...");
        }

        if (user.getPassword().length() < 6) {
            throw new RuntimeException("UserPassword have to more or equal 6 characters");
        }

        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user);
        return user;
    }
}
