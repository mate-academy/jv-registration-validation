package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can not be null");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RuntimeException("login must contain more than 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Password must be longer than 6");
        }

        if (user.getAge() < 18) {
            throw new RuntimeException("User must be over 18");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such User already exists");
        }
        return storageDao.add(user);
    }
}
