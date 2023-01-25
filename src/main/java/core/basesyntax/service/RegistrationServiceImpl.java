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
        if (user.getLogin() == null) {
            throw new RuntimeException("The user login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("The user with such login already exists in the Storage");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("The user should be at least 18 years old");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("The user password should be at least 6 characters");
        }
        return storageDao.add(user);
    }
}
