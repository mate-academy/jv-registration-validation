package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_AGE = 140;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNull(user);
        checkLogin(user);
        checkAge(user);
        checkPassword(user);
        storageDao.add(user);
        return user;
    }

    private void checkForNull(User user) {
        if (user == null) {
            throw new InvalidRegistrationDataException("Register argument is null");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || user.getLogin().equals("")) {
            throw new InvalidRegistrationDataException("Invalid login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidRegistrationDataException("User with this login"
                    + "is already registered");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE || user.getAge() > MAX_AGE) {
            throw new InvalidRegistrationDataException("Age must be between 18 and 140");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidRegistrationDataException("Password is too short");
        }
    }
}
