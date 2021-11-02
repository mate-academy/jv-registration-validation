package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null
                || user.getAge() == null) {
            throw new RuntimeException("Fill user information with valid data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Such User already registered");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Users age must be more than 18");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password must be more than 6 symbols");
        }
        return storageDao.add(user);
    }
}
