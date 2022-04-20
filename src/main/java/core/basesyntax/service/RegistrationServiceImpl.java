package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        if (user == null || user.getAge() == null
                || user.getLogin() == null
                || user.getPassword() == null) {
            throw new RuntimeException("User is null");
        }
        StorageDao storageDao = new StorageDaoImpl();
        if (user.getAge() >= 18 && storageDao.get(user.getLogin()) == null
                && user.getPassword().length() >= 6) {
            storageDao.add(user);
            return user;
        } else {
            throw new RuntimeException("Incorrect data");
        }
    }
}
