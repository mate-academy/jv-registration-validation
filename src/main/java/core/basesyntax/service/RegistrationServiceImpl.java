package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_ACCEPTABLE_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateNullValues(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void validateNullValues(User user) {
        if (user == null) {
            throw new RegistrationException("The value of User can not be equal to null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("The value of User login can not be equal to null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("The value of User password can not be equal to null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("The value of User age can not be equal to null");
        }
    }

    private void validateLogin(String login) {
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    "The user login's length has to be at least 6 characters"
            );
        }
        User isUserWithTheSameLogin = storageDao.get(login);
        if (isUserWithTheSameLogin != null) {
            throw new RegistrationException("The user with the pointed login already exists");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "The user password's length has to be at least 6 characters"
            );
        }
    }

    private void validateAge(int age) {
        if (age < MIN_USER_ACCEPTABLE_AGE) {
            throw new RegistrationException("Impossible to register user younger then 18 years");
        }
    }
}
