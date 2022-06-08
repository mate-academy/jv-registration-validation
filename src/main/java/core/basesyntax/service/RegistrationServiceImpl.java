package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Wrong login of registering user (cannot be empty or already exist)");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Password is incorrect (cannot be empty or less then six symbols)");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new RuntimeException("The registered user must be an adult (or age field cannot be empty)");
        }
        return storageDao.add(user);
    }
}
