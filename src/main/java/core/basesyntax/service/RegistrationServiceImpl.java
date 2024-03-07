package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationFailureException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LEGAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNotNull(user);
        checkUserAge(user);
        checkUserLoginLength(user);
        checkUserPasswordLength(user);
        checkUserNotRegisteredYet(user);
        return storageDao.add(user);
    }

    private void checkUserNotNull(User user) {
        if (user == null) {
            throw new RegistrationFailureException("User registration failed due to user is null");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_LEGAL_AGE) {
            throw new RegistrationFailureException("User's age is less than"
                    + MIN_LEGAL_AGE + " or not specified");
        }
    }

    private void checkUserPasswordLength(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationFailureException("Password is shorter than "
                    + MIN_LOGIN_PASSWORD_LENGTH + " or not specified");
        }
    }

    private void checkUserLoginLength(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationFailureException("Login is shorter than "
                    + MIN_LOGIN_PASSWORD_LENGTH + " or not specified");
        }
    }

    private void checkUserNotRegisteredYet(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailureException("Such user is already registered");
        }
    }
}
