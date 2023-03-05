package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_MIN = 18;
    private static final int MIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age is 'null' it's bad");
        }
        if (user.getAge() < AGE_MIN) {
            throw new RuntimeException("You are still a child");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login is 'null' it's bad");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("You are late, this login already exists");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is 'null' it's bad");
        }
        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new RuntimeException("Password is short");
        }
        return storageDao.add(user);
    }
}



