package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LEGAL_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User is null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("login == null");
        }
        StorageDao storageDao = new StorageDaoImpl();
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with same login already existing");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age is null");
        }
        if (user.getAge() < LEGAL_AGE) {
            throw new RuntimeException("You must be 18 or older to register");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password is null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must has at least 6 characters");
        }
        return storageDao.add(user);
    }
}
