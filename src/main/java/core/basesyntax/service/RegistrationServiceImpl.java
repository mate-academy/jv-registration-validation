package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User do not exist");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("User login can not be empty");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new RuntimeException("User must be older " + USER_MIN_AGE);
        }
        if (user.getPassword() != null && user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("User password is too short,"
                    + " password must be at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already register");
        }
        return storageDao.add(user);
    }

    private void checkUserLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already register");
        }
    }
}
