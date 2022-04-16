package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("user is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("user with this login already exist");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("your age is less then 18");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("password length less then 6 symbols");
        }
        return user;
    }
}
