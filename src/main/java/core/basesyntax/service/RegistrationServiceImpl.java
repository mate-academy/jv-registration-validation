package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < 18
                || user.getPassword().length() < 6
                || Storage.people.contains(user)) {
            throw new RuntimeException("Cannot register this user!");
        }
        return storageDao.add(user);
    }
}
