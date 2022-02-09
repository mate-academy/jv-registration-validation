package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    public static final int MINIMUM_USER_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfNull(user);
        checkAge(user);
        checkPassword(user);
        checkLogin(user);
        return storageDao.add(user);
    }

    private void checkIfNull(User user) {
        if (user == null) {
            throw new RuntimeException("You tried to register user with NULL");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Your password is too short. It should be at least "
                + MINIMUM_PASSWORD_LENGTH + "symbols.");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MINIMUM_USER_AGE || user.getAge() == null) {
            throw new RuntimeException("You can not register user who is less than "
                + MINIMUM_USER_AGE);
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Login is null");
        }
    }
}
