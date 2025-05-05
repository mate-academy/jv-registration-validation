package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_SIZE = 6;
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);
        return storageDao.add(user);
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
                    "Invalid user's password for registration: "
                            + "password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationException(
                    "Invalid user's password for registration: "
                            + "length of password is: [" + user.getPassword().length()
                            + "], allowed minimal length is: ["
                            + MIN_LOGIN_SIZE + "]");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException(
                    "Invalid user's age for registration: "
                            + "age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    "Invalid user's age for registration: "
                            + "user's age is: [" + user.getAge()
                            + "], allowed minimal age is: ["
                            + MIN_AGE + "]");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException(
                    "Invalid user's login for registration: "
                            + "login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_SIZE) {
            throw new RegistrationException(
                    "Invalid user's login for registration: "
                            + "user's login length: ["
                            + user.getLogin().length()
                            + "] allowed minimal login length is: ["
                            + MIN_LOGIN_SIZE + "]");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login: "
                    + user.getLogin()
                    + " is already exist. Existing user cannot be registered.");
        }
    }
}
