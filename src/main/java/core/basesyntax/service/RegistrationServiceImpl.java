package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.excepction.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE = 18;
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserIsNull(user);
        isAgeNull(user);
        isAgeValid(user);
        checkLoginIsNull(user);
        checkLoginIsValid(user);
        isPasswordNull(user);
        checkPasswordIsValid(user);
        checkIfUserExists(user);
        return storageDao.add(user);
    }

    private void checkUserIsNull(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can't be null");
        }
    }

    private void isAgeNull(User user) {
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age can't be null");
        }
    }

    private void isAgeValid(User user) {
        if (user.getAge() < VALID_AGE) {
            throw new UserRegistrationException("Age is not valid,"
                    + "must be equals or older than 18");
        }
    }

    private void checkLoginIsNull(User user) {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("login can't be null");
        }
    }

    private void checkLoginIsValid(User user) {
        if (user.getLogin().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Login length must be equals or loner than 6");
        }
    }

    private void isPasswordNull(User user) {
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can't be null");
        }
    }

    private void checkPasswordIsValid(User user) {
        if (user.getPassword().length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Password length must be equals or loner than 6");
        }
    }

    private void checkIfUserExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User already exists");
        }
    }
}
