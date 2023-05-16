package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_SIZE = 6;
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final String INVALID_PASSWORD_MESSAGE =
            "Invalid user's password for registration: ";
    private static final String INVALID_AGE_MESSAGE =
            "Invalid user's age for registration: ";
    private static final String INVALID_LOGIN_MESSAGE =
            "Invalid user's login for registration: ";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);
        return isExistingLogin(user) ? storageDao.add(user) : null;
    }

    private boolean isExistingLogin(User user) {
        return storageDao.get(user.getLogin()) == null;
    }

    private void validateUserData(User user) {
        checkNullInstance(user);
        validateLogin(user);
        validateAge(user);
        validatePassword(user);
    }

    private void checkNullInstance(User user) {
        if (user == null) {
            throw new RegistrationException(
                    "User does not exist, must be not null-value");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException(
                    INVALID_PASSWORD_MESSAGE
                            + "password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationException(
                    INVALID_PASSWORD_MESSAGE
                            + "length of password is: [" + user.getPassword().length()
                            + "], allowed minimal length is: ["
                            + MIN_LOGIN_SIZE + "]");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException(
                    INVALID_AGE_MESSAGE
                            + "age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    INVALID_AGE_MESSAGE
                            + "user's age is: [" + user.getAge()
                            + "], allowed minimal age is: ["
                            + MIN_AGE + "]");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException(
                    INVALID_LOGIN_MESSAGE + "login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_SIZE) {
            throw new RegistrationException(
                    INVALID_LOGIN_MESSAGE + "user's login length: ["
                            + user.getLogin().length()
                            + "] allowed minimal login length is: ["
                            + MIN_LOGIN_SIZE + "]");
        }
    }
}
