package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User: " + user.getLogin() + " is already exist!");
        }
        if (user.getLogin().length() == 0) {
            throw new RuntimeException("User`s login is to short!");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length is less than 6 characters!");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("Age is null!");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("Age is less than 18 years!");
        }
        storageDao.add(user);
        return user;
    }
}
