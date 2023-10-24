package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String MESSAGE_EXCEPTION_USER_IS_EXISTS
            = "An account with this login already exists. Entered login: ";
    private static final String MESSAGE_EXCEPTION_AGE_NULL = "The age can't be null.";
    private static final String MESSAGE_EXCEPTION_LOGIN_NULL = "The login can't be null.";
    private static final String MESSAGE_EXCEPTION_MIN_AGE =
            "The age of the user must not be less than: ";
    private static final String MESSAGE_EXCEPTION_MIN_LOGIN_LENGTH =
            "The minimum number of characters that make up the login should be: ";
    private static final String MESSAGE_EXCEPTION_MIN_PASSWORD_LENGTH =
            "The minimum number of characters that make up the password should be: ";
    private static final String MESSAGE_EXCEPTION_PASSWORD_NULL = "The password can't be null.";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        validateUserIsExist(user);
        validateForNull(user);
        validateFormat(user);
        validateAge(user);
    }

    private void validateUserIsExist(User user) {
        if (storageDao.get(user.getLogin()).equals(user)) {
            throw new RegistrationException(MESSAGE_EXCEPTION_USER_IS_EXISTS + user.getLogin());
        }
    }

    private void validateForNull(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException(MESSAGE_EXCEPTION_PASSWORD_NULL);
        }
        if (user.getLogin() == null) {
            throw new RegistrationException(MESSAGE_EXCEPTION_LOGIN_NULL);
        }
        if (user.getAge() == null) {
            throw new RegistrationException(MESSAGE_EXCEPTION_AGE_NULL);
        }
    }

    private void validateFormat(User user) {
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(MESSAGE_EXCEPTION_MIN_PASSWORD_LENGTH
                    + MIN_PASSWORD_LENGTH);
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(MESSAGE_EXCEPTION_MIN_LOGIN_LENGTH
                    + MIN_LOGIN_LENGTH);
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(MESSAGE_EXCEPTION_MIN_AGE + MIN_AGE);
        }
    }
}
