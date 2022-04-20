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
            throw new RuntimeException("user is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("login " + user.getLogin() + " already exists");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Min age is 18");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RuntimeException("Invalid password! min length for password - 6!");
        }
        storageDao.add(user);
        return user;
    }
}
