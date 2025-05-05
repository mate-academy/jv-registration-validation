package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null");
        }
        if (user.getAge() < 18 || user.getAge() == null) {
            throw new RuntimeException("Your age must be at least 18");
        }
        if (user.getPassword().length() < 6 || user.getPassword() == null) {
            throw new RuntimeException("Your password must be at least 18");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login: " + user.getLogin() + " already created");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Please fill the login field");
        }
        return storageDao.add(user);
    }
}
