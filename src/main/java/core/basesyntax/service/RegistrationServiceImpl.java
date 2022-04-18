package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ALLOW_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("user is null");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("user Login is incorrect");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("user with this login already exist");
        }
        if (user.getAge() == null || user.getAge() == 0) {
            throw new RuntimeException("age is incorrect");
        } else if (user.getAge() < ALLOW_AGE) {
            throw new RuntimeException("your age is less then 18");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("password length less then 6 symbols");
        }
        return storageDao.add(user);
    }
}
