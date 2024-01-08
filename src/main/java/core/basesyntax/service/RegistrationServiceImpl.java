package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final String EXISTING_USER_MESSAGE = "User with this login already exist";
    private static final String INVALID_AGE = "Not valid age. Min allowed age: " + MIN_AGE;
    private static final String INVALID_PASSWORD_LENGTH = "Not valid password length."
            + " Min allowed password length is " + MIN_PASSWORD_LENGTH;
    private static final String INVALID_LOGIN_LENGTH = "Not valid password length."
            + " Min allowed password length is " + MIN_PASSWORD_LENGTH;
    private static final String LOGIN_CANNOT_BE_NULL = "Login can't be null";
    private static final String PASSWORD_CANNOT_BE_NULL = "Password can't be null";
    private static final String AGE_CANNOT_BE_NULL = "Age can't be null";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        checkNullValues(user);
        checkLength(user);
        checkAge(user);
        checkIfUserAlreadyRegistered(user);
        return storageDao.add(user);
    }

    private void checkLength(User user) throws RegistrationException {
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(INVALID_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(INVALID_PASSWORD_LENGTH);
        }
    }

    private void checkAge(User user) throws RegistrationException {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(INVALID_AGE);
        }
    }

    private boolean checkNullValues(User user) throws RegistrationException {
        if (user.getLogin() == null) {
            throw new RegistrationException(LOGIN_CANNOT_BE_NULL);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException(PASSWORD_CANNOT_BE_NULL);
        }
        if (user.getAge() == null) {
            throw new RegistrationException(AGE_CANNOT_BE_NULL);
        }
        return true;
    }

    private void checkIfUserAlreadyRegistered(User user) throws RegistrationException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(EXISTING_USER_MESSAGE);
        }
    }
}
