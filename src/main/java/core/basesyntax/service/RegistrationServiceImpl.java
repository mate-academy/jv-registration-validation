package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidation(user);
        storageDao.add(user);
        return user;
    }

    private void userValidation(User user) {
        if (user == null) {
            throw new RegistrationException("No user received");
        }
        if (user.getAge() == null || user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("Age must be at least 18");
        }
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Invalid login");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
    }
}
