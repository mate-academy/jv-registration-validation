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
            throw new RuntimeException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login is null");
        }
        if (user.getLogin().length() == 0) {
            throw new RuntimeException("User login length = 0");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age under " + MIN_AGE);
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age is null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User password is null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        return storageDao.add(user);
    }
}
