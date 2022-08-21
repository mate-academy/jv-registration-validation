package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int AGE_MIN_VALUE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin().length() > 0
                && storageDao.get(user.getLogin()) == null
                && user.getAge() >= AGE_MIN_VALUE
                && user.getPassword().length() >= PASSWORD_MIN_LENGTH) {
            storageDao.add(user);
            return user;
        }
        throw new RuntimeException();
    }
}
