package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ALLOWED_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("No user");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Invalid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exist");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Invalid age");
        }
        if (user.getAge() < ALLOWED_AGE) {
            throw new RuntimeException("User must be at least 18 years old");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Invalid password");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("User password must be at least 6 characters");
        }
        return storageDao.add(user);
    }
}
