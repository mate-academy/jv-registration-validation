package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user != null && user.getLogin() != null && user.getPassword() != null
                && storageDao.get(user.getLogin()) == null
                && user.getAge() >= 18 && user.getAge() <= Integer.MAX_VALUE
                && user.getPassword().length() >= 6) {
            return storageDao.add(user);
        } else {
            throw new RuntimeException("Invalid data");
        }
    }
}
