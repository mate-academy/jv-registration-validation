package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getAge() == null || user.getPassword() == null
                || user.getLogin() == null) {
            throw new RuntimeException("User fields cannot be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Cannot register user with age less than 18");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Password must be at least 6 character");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with the same login is already exist.");
        }
    }
}
