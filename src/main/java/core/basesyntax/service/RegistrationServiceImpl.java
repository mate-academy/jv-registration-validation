package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null!");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is null!");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD) {
            throw new RuntimeException("Password isn't correct!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login already exist!");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age isn't correct!");
        }
        storageDao.add(user);
        return user;
    }
}
