package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 150;
    private static final int MAX_LOGIN_LENGTH = 256;
    private static final int MAX_PASSWORD_LENGTH = 256;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User doesn't exist or null!");
        }
        if (user.getLogin() == null || user.getLogin() == "") {
            throw new RuntimeException("Login must NOT be null or empty!");
        }
        if (user.getLogin().length() > MAX_LOGIN_LENGTH) {
            throw new RuntimeException("Login is too long!");
        }
        if (user.getAge() == null || user.getAge().intValue() < MIN_AGE) {
            throw new RuntimeException("Age must be 18 and over!");
        }
        if (user.getAge().intValue() > MAX_AGE) {
            throw new RuntimeException("Hello, Duncan MacLeod! In the end, there can be only one."
                    + " Not you.");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be at least 6 characters long!");
        }
        if (user.getPassword().length() > MAX_PASSWORD_LENGTH) {
            throw new RuntimeException("Password too long!");
        }
        storageDao.add(user);
        return user;
    }
}
