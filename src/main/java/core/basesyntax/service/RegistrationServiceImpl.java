package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ADULT_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
        return storageDao.add(user);
    }

    private void checkAge(User user) {
        if (user.getAge() < ADULT_AGE) {
            throw new RuntimeException("Can't register user age under 18");
        }
        if (user.getAge() >= ADULT_AGE) {
            storageDao.add(user);
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be NULL");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("User with such login already exists");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be NULL");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password too short");
        }
    }
}
