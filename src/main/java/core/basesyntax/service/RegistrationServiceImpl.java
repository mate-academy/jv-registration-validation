package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException();
        }
        if (user.getAge() == null || user.getAge() < 18
                || user.getPassword() == null || user.getPassword().length() < 6
                || user.getLogin() == null) {
            return null;
        }
        if (storageDao.get(user.getLogin()) != null) {
            return null;
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
