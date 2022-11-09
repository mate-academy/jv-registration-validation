package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getAge() == null || user.getPassword() == null) {
            throw new NullPointerException("Null value is not acceptable");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age must be at least 18");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Password must contain at least 6 characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exist");
        } else {
            storageDao.add(user);
            return user;
        }
    }
}
