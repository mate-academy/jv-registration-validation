package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                // if user doesnt exist in storage we can get NPE, check
                // for login not null.storageDao.get(user.getLogin()).getLogin - could be null
                || user.getLogin().equals(storageDao.get(user.getLogin()))
                || (user.getAge() != null && user.getAge() < 18)) {
            throw new RuntimeException();
        }

        return storageDao.add(user);
    }
}
