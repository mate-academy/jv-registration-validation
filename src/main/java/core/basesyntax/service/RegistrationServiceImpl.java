package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_ACCEPTABLE_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user);
        checkIfLoginIsFree(user);
        checkPassword(user);
        checkLAge(user);
        return storageDao.add(user);
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must contain at least "
                    + MIN_LOGIN_LENGTH + " characters.");
        }
    }

    private void checkIfLoginIsFree(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must contain at least "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
    }

    private void checkLAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_ACCEPTABLE_AGE) {
            throw new RegistrationException("Not valid age! Min allowed age is "
                    + MIN_ACCEPTABLE_AGE + ".");
        }
    }
}
