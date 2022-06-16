package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("There is invalid user`s data.");
        }
        if (user.getAge() < 18 || (storageDao.get(user.getLogin()) != null)
                || (user.getPassword().length() < 6)) {
            return null;
        }
        storageDao.add(user);
        return user;
    }
}
