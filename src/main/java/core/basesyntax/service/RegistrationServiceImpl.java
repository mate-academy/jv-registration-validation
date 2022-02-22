package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isNullUserData(user) || isNoValidUserRegistration(user, storageDao)) {
            throw new RuntimeException();
        }

        return storageDao.add(user);
    }

    private boolean isNullUserData(User user) {
        return user == null || user.getAge() == null || user.getLogin() == null
                || user.getPassword() == null;
    }

    private boolean isNoValidUserRegistration(User user, StorageDao storageDao) {
        return user.getAge() < 18 || user.getPassword().length() < 6
                || storageDao.get(user.getLogin()) != null;

    }
}
