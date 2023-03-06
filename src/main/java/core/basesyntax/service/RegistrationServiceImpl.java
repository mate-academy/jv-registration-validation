package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new RegistrationException("Invalid data. User can't be null");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("Invalid data. Login can't be empty or null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Invalid data. Password can't be null");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD) {
            throw new RegistrationException("The password must contain at least "
                    + MINIMUM_PASSWORD + " characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Invalid data. Age can't be null");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Age can't be negative");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("Age can't be less than " + MINIMUM_AGE + "!");
        }
    }
}
