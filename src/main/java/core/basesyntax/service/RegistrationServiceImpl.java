package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_VALUE_OF_PASSWORD = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("No valid age");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_VALUE_OF_PASSWORD) {
            throw new RuntimeException("No valid password");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age must be greater than 18");
        }

        if (storageDao.get(user.getLogin()).getLogin() == null) {
            storageDao.add(user);
        }
        return user;
    }
}
