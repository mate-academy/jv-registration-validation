package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("User age can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Age is not valid");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("This login is already exist");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length should be 6 or more symbols");
        }
        return storageDao.add(user);
    }
}
/*
if (user.getPassword().length() == 0) {
            throw new RuntimeException("Password field is empty!");
        }
 */
