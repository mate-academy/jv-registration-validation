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
        isDataNull(user);
        isUserInvalid(user);
        return storageDao.add(user);
    }

    private void isDataNull(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login cannot be null!");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age cannot be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password cannot be null!");
        }
    }

    private void isUserInvalid(User user) {
        isLoginLengthCorrect(user.getLogin());
        isPasswordLengthCorrect(user.getPassword());
        isAgeAppropriate(user.getAge());
        isNotExisting(user.getLogin());
    }

    private void isNotExisting(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("The user already exists in storage!");
        }
    }

    private void isLoginLengthCorrect(String login) {
        if (login.length() < LOGIN_MINIMAL_LENGTH) {
            throw new RegistrationException(
                    "Login has to be " + LOGIN_MINIMAL_LENGTH + " letters or longer!");
        }
    }

    private void isPasswordLengthCorrect(String password) {
        if (password.length() < PASSWORD_MINIMAL_LENGTH) {
            throw new RegistrationException(
                    "Password has to be " + PASSWORD_MINIMAL_LENGTH + " chars or longer!");
        }
    }

    private void isAgeAppropriate(Integer age) {
        if (age < USER_MINIMAL_AGE) {
            throw new RegistrationException(
                    "To register user must be older than " + USER_MINIMAL_AGE + " years!");
        }
    }
}
