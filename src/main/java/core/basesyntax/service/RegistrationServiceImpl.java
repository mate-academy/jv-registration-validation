package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can`t be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exists");
        }
        if (user.getAge() < 1) {
            throw new RuntimeException("Impossible age");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Oh my boi, U R 2 young 4 dat");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password is too short");
        }
        storageDao.add(user);
        return user;
    }
}
