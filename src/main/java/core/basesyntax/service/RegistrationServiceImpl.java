package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    /*

    there is no user with such login in the Storage yet
    user's login is at least 6 characters
    user's password is at least 6 characters
    user's age is at least 18 years old

    */
    private static final int MIN_USER_LOGIN_SIZE = 6;
    private static final int MIN_USER_PASSWORD_SIZE = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        checkIfUserExists(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        validateNotNull(user, "User cannot be null!");
        validateNotNullNotEmpty(user.getLogin(), "Login cannot be null or empty!");
        validateNotNullNotEmpty(user.getPassword(), "Password cannot be null or empty!");
        validateMinLength(user.getLogin(), MIN_USER_LOGIN_SIZE, "Login");
        validateMinLength(user.getPassword(), MIN_USER_PASSWORD_SIZE, "Password");
        validateAge(user.getAge());
    }

    private void validateNotNull(Object value, String errorMessage) {
        if (value == null) {
            throw new RegistrationException(errorMessage);
        }
    }

    private void validateNotNullNotEmpty(String value, String errorMessage) {
        if (value == null || value.isEmpty()) {
            throw new RegistrationException(errorMessage);
        }
    }

    private void validateMinLength(String value, int minLength, String field) {
        if (value.length() < minLength) {
            throw new RegistrationException(field + " must be at least " + minLength + " characters long!");
        }
    }

    private void validateAge(int age) {
        if (age < MIN_USER_AGE) {
            throw new RegistrationException("Invalid age: " + age + ". Min allowed age is " + MIN_USER_AGE);
        }
    }

    private void checkIfUserExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login " + user.getLogin() + " already exists!");
        }
    }
}