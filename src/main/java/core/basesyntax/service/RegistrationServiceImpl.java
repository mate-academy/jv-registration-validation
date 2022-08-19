package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User should not be null");
        }
        for (User userLogin : Storage.people) {
            if (userLogin.getLogin().equals(user.getLogin())) {
                throw new NullPointerException("User should have unique login");
            }
        }
        return storageDao.add(user);
    }
}
