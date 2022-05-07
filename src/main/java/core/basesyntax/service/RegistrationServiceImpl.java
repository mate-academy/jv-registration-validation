package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("Data can't be null");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login already exists");
        }

        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password can't be less than 6 characters");
        }

        if (user.getAge() < 18) {
            throw new RuntimeException("Age under 18 is not allowed");
        }
        storageDao.add(user);
        return user;
    }
}
