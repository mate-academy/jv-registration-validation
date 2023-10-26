package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final String MESSAGE_USER_NULL = "User can't be null";
    private static final String MESSAGE_LOGIN_NULL = "Login can't be null";
    private static final String MESSAGE_PASSWORD_NULL = "Password can't be null";
    private static final String MESSAGE_AGE_NULL = "Age can't be null";
    private static final String MESSAGE_ALREADY_REGISTERED = "the user is already registered";
    private static final String MESSAGE_LOGIN_LENGTH = "the login must be more than "
            + MIN_LOGIN_LENGTH + " characters";
    private static final String MESSAGE_PASSWORD_LENGTH = "the password must be more than "
            + MIN_PASSWORD_LENGTH + " characters";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNull(user);
        checkLength(user);
        checkDB(user);
        return storageDao.add(user);
    }

    private void checkForNull(User user) {
        if (user == null) {
            throw new RegistrationException(MESSAGE_USER_NULL);
        }
        if (user.getLogin() == null) {
            throw new RegistrationException(MESSAGE_LOGIN_NULL);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException(MESSAGE_PASSWORD_NULL);
        }
        if (user.getAge() == null) {
            throw new RegistrationException(MESSAGE_AGE_NULL);
        }
    }

    private void checkLength(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE);
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(MESSAGE_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(MESSAGE_PASSWORD_LENGTH);
        }
    }

    private void checkDB(User user) {
        if (user.equals(storageDao.get(user.getLogin()))
                || storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Not valid user login: "
                    + user.getLogin());
        }
    }
}
