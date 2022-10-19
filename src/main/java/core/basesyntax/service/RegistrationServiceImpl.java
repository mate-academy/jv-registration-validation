package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int minPassLength = 6;
    private static final int adultAge = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NullPointerException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("It's already exists user by login " + user.getLogin());
        }
        if (user.getAge() <= adultAge) {
            throw new RuntimeException("Can't register user with age " + user.getAge());
        }
        if (user.getPassword().length() < minPassLength) {
            throw new RuntimeException("Password must contains at least "
                    + minPassLength + " characters");
        }
        return storageDao.add(user);
    }
}
