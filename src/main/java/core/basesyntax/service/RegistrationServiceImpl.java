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
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login is null");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password is null");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("Age is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age is less than " + MIN_AGE);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is less than " + MIN_PASSWORD_LENGTH);
        }
        storageDao.add(user);
        return user;
    }
}
