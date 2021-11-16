package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHARS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Error! User can't be null");
        }
        if (user.getLogin() == null
                || user.getAge() == null
                || user.getPassword() == null) {
            throw new RuntimeException("Login, Age or password can't be null");
        }
        if (user.getPassword().length() < MIN_CHARS) {
            throw new RuntimeException("Password must have at lest 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age must be at least 18 or more");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with this login already exist");
        }
        return storageDao.add(user);
    }
}
