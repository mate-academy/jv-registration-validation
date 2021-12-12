package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    @Override
    public User register(User user) {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        if (user != null && storageDao.get(user.getLogin()) == null
                && user.getAge() >= 18 && user.getPassword().length() >= 6) {
            return storageDao.add(user);
        } else {
            throw new RuntimeException();
        }
    }
}
