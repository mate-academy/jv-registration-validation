package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE_VALUE = 18;
    private static final int MIN_PASSWORD_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null
                || storageDao.get(user.getLogin()) != null
                || user.getLogin() == null
                || user.getAge() < MIN_AGE_VALUE
                || user.getAge() == null
                || user.getPassword().length() < MIN_PASSWORD_SIZE
                || user.getPassword() == null) {
            throw new RuntimeException("Invalid User parameters");
        }
        return storageDao.add(user);
    }
}
