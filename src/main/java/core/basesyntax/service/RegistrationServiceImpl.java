package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_AGE = 18;
    private static final int MIN_VALID_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new NullPointerException("Can't register null user!");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("User's password cannot be empty");
        }
        if (user.getPassword().length() < MIN_VALID_PASSWORD_LENGTH) {
            throw new RuntimeException("User's password length cannot be less then "
                    + MIN_VALID_PASSWORD_LENGTH);
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("User's age cannot be empty");
        }
        if (user.getAge() < MIN_VALID_AGE) {
            throw new RuntimeException("User's age cannot be less then " + MIN_VALID_AGE);
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("User's login cannot be empty");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("User with this login already exists!");
        }
    }
}
