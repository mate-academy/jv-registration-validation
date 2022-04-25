package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User should be at least 18 years old");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password minimal length must be 6 or more");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login should not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with that login already exist");
        }
        storageDao.add(user);
        return user;
    }
}
