package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login cannot be null");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Password cannot be null and must and must contain 6 or more characters");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new RuntimeException("Age cannot be null and must be over 18 year");
        }
        storageDao.add(user);
        return user;
    }
}
