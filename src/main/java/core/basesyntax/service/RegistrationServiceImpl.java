package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("You can't register a user with any null parameters!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("You can't register a user under the age of 18!");
        }
        if (isUserExist(user)) {
            throw new RuntimeException("User with login:" + user.getLogin() + " is already exist.");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("User password must be at least 6 characters.");
        }
        storageDao.add(user);
        return user;
    }

    private boolean isUserExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            return true;
        }
        return false;
    }
}
