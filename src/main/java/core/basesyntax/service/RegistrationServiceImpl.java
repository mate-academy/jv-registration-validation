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
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        return storageDao.add(user);
    }

    private static void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Minimal length of password is " + MIN_PASSWORD_LENGTH);
        }
    }

    private static void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age connot be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("Minimum user age for registration:"
                    + MIN_USER_AGE + " . Your age is not suitable");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login cannot be null");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("User " + user.getLogin() + " alreasy exist");
        }
    }
}
