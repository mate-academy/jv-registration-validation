package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("Data must be filled");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RuntimeException("Login must be at least 6 characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be at least 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age must be more than " + MIN_AGE + " years");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login area must be filled");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age area must be filled");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password area must be filled");
        }
        return storageDao.add(user);
    }
}
