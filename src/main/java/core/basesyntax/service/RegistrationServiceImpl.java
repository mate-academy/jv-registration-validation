package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already registered");
        }
        if (user.getAge() < MIN_AGE || user.getAge() == null) {
            throw new RuntimeException("Age is less than " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD || user.getPassword() == null) {
            throw new RuntimeException("Length password less than " + MIN_LENGTH_PASSWORD);
        }
        return storageDao.add(user);
    }
}
