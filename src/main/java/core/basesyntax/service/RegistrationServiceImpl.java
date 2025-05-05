package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkNullValues(user);
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        return storageDao.add(user);
    }

    private void checkLogin(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with such login already exist");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("User age under age or wrong input");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password is too weak. "
                    + "It should be at least 6 characters long");
        }
    }

    private void checkNullValues(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User's login can't be null.");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User's age can't be null.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("User's password can't be null.");
        }
    }
}
