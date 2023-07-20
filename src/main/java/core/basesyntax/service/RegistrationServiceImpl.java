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
        checkIsUserNull(user);
        checkLoginForNull(user);
        checkPasswordForNull(user);
        checkLoginsLength(user);
        checkPasswordsLength(user);
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
        int usersAge = user.getAge();
        if (usersAge < MIN_AGE) {
            throw new RegistrationException("User's is not valid! It's " + usersAge + ", but min "
                    + "age is " + MIN_AGE);
        }
    }

    private static void checkPasswordsLength(User user) {
        int passwordsLength = user.getPassword().length();
        if (passwordsLength < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User's length of password is " + passwordsLength
                    + ". It shouldn't be shorter than 6 characters!");
        }
    }

    private static void checkLoginsLength(User user) {
        int loginsLength = user.getLogin().length();
        if (loginsLength < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("User's length of login is " + loginsLength
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

    private static void checkIsUserNull(User user) {
        if (user == null) {
            throw new RegistrationException("User is null!");
        }
    }
}
