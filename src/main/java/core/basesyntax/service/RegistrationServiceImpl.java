package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String DEFAULT_EXCEPTION_TEXT = "Can't register this user. ";
    private static final String EMPTY_STRING = "";
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateUserNonDuplicates(user);
        validateUserFields(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throwException("User can't be null");
        }
    }

    private void validateUserFields(User user) {
        validateLogin(user);
        validatePassword(user);
        validateUserAge(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throwException("User login can't be null");
        }
        if (user.getLogin().equals(EMPTY_STRING)) {
            throwException("Login can't be empty");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throwException("User password can't be null");
        }
        if (user.getPassword().equals(EMPTY_STRING)) {
            throwException("Password can't be empty");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throwException("Password must contain more than 6 symbols");
        }
    }

    private void validateUserAge(User user) {
        if (user.getAge() == null) {
            throwException("User age can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throwException("User must be of legal age");
        }
    }

    private void validateUserNonDuplicates(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throwException("User already exist");
        }
    }

    private void throwException(String message) {
        throw new RuntimeException(DEFAULT_EXCEPTION_TEXT + message);
    }
}
