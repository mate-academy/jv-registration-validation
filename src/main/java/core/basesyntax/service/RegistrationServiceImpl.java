package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("The user in null");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("The password is too short");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("The user is too young");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such user is already exist");
        }
        storageDao.add(user);
        return user;
    }

    @Override
    public boolean remove(User user) {
        return storageDao.remove(user);
    }
}
