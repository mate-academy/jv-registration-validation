package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Empty user");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Invalid login");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Invalid age");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Invalid password");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Username already taken");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User must be at least 18 years old ");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Password must be longer than 6 characters");
        }
        return storageDao.add(user);
    }
}
