package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserForNull(user);
        checkLoginForNull(user);
        checkPasswordForNull(user);
        checkLoginLength(user);
        checkPasswordLength(user);
        checkAge(user);
        checkUserExistence(user);
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }

    private void checkUserExistence(User user) {
        if (isUserExist(user)) {
            throw new RegistrationException("The storage already have user with login "
                    + user.getLogin());
        }
    }

    private boolean isUserExist(User user) {
        return storageDao.get(user.getLogin()) != null;
    }

    private static void checkAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's is not valid! It's " + user.getAge()
                    + ", but min age is " + MIN_AGE);
        }
    }

    private static void checkPasswordLength(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User's length of password is "
                    + user.getPassword().length() + ". It shouldn't be shorter than 6 characters!");
        }
    }

    private static void checkLoginLength(User user) {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User's length of login is " + user.getLogin().length()
                    + ". It shouldn't be shorter than 6 characters!");
        }
    }

    private static void checkPasswordForNull(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password is null!");
        }
    }

    private static void checkLoginForNull(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login is null!");
        }
    }

    private static void checkUserForNull(User user) {
        if (user == null) {
            throw new RegistrationException("User is null!");
        }
    }
}
