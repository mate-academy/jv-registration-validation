package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("There is already user with such login");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("User must be 18 years old or older");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters long");
        }
        storageDao.add(user);
        return user;
    }
}
