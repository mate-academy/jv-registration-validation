package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RuntimeException("user is null or login is null/empty");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new RuntimeException("or age null or user is too young or age is unbelievable");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("user's password is too short");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such a user already exists with this login");
        }
        return storageDao.add(user);
    }
}
