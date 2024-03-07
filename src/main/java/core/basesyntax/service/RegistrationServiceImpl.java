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
        checkUserAge(user.getAge());
        checkUserLoginLength(user.getLogin().length());
        checkUserPasswordLength(user.getPassword().length());
        checkUserNotRegisterdYet(user);
        return user;
    }

    private void checkUserNotNull(User user) {
        if (user == null) {
            throw    new RegistrationFailureException("User registration failed due to the invalid data");
        }
    }

    private void checkUserAge(int age) {
        if (age < MIN_LEGAL_AGE ) {
            throw new RegistrationFailureException("User's age is less than 18");
        }
    }

    private void checkUserPasswordLength(int passwordLength) {
        if (passwordLength < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationFailureException("User's password should be at least 6 characters long");
        }
    }

    private void checkUserLoginLength(int loginLength) {
        if (loginLength < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationFailureException("User's login should be at least 6 characters long");
        }
    }

    private void checkUserNotRegisterdYet(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailureException("Such user is already registered");
        }
    }
}
