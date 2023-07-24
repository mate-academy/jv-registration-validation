package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MINIMAL_LENGTH = 6;
    private static final int PASSWORD_MINIMAL_LENGTH = 6;
    private static final int USER_MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || !isUserValid(user)) {
            throw new RegistrationException("The user data is invalid!");
        }
        return storageDao.add(user);
    }

    private boolean isUserValid(User user) {
        if (! (isNotExisting(user.getLogin())
                && isLoginLengthCorrect(user.getLogin())
                && isPasswordLengthCorrect(user.getPassword())
                && isAgeAppropriate(user.getAge()))) {
            throw new RegistrationException("Your data is invalid!");
        }
        return true;
    }

    private boolean isNotExisting(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("The user already exists in storage!");
        }
        return true;
    }

    private boolean isLoginLengthCorrect(String login) {
        if (login == null || login.length() < LOGIN_MINIMAL_LENGTH) {
            throw new RegistrationException(
                    "Login has to be " + LOGIN_MINIMAL_LENGTH + " letters or longer!");
        }
        return true;
    }

    private boolean isPasswordLengthCorrect(String password) {
        if (password == null || password.length() < PASSWORD_MINIMAL_LENGTH) {
            throw new RegistrationException(
                    "Password has to be " + PASSWORD_MINIMAL_LENGTH + " chars or longer!");
        }
        return true;
    }

    private boolean isAgeAppropriate(Integer age) {
        if (age == null || age < USER_MINIMAL_AGE) {
            throw new RegistrationException(
                    "To register user must be older than " + USER_MINIMAL_AGE + " years!");
        }
        return true;
    }

}
