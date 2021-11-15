package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        StorageDao storageDao = new StorageDaoImpl();
        if (user.getLogin() == null || user.getAge() == null ){
            throw new RuntimeException("Login or Age are not correct")
        }
        if (storageDao.get(user.getLogin()) == null && user.getAge() >=18 && user.getPassword().length() >= 6) {
            return storageDao.add(user);
        }
        return null;
    }
}
