package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final short MINIMUM_PASSWORD_LENGTH = 6;
    private static final short MINIMUM_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with Login: " + user.getLogin() + " already exists");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password cannot be null and must contain "
                    + MINIMUM_PASSWORD_LENGTH + " or more characters");
        }
        if (user.getAge() == null || user.getAge() < MINIMUM_USER_AGE) {
            throw new RuntimeException("Age cannot be null and must be over "
                    + MINIMUM_USER_AGE + " year");
        }
        storageDao.add(user);
        return user;
    }
}
