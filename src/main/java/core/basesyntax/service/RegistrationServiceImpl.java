package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserIsNotNull(user);
        validateLogin(user);
        validateAge(user);
        validatePassword(user);
        return storageDao.add(user);
    }

    private void validateUserIsNotNull(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid login: " + user.getLogin()
                    + ". Min login's length is " + MIN_LOGIN_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("The user with login: " + user.getLogin()
                    + " is already exist.");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Not valid password. "
                    + "Min password's length is " + MIN_LOGIN_LENGTH);
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age must be specified");
        }
        if (user.getAge() < MIN_AGE) {
            String message = "Not valid age: " + user.getAge() + ". Min allowed age is " + MIN_AGE;
            throw new RegistrationException(message);
        }
        if (user.getAge() > MAX_AGE) {
            String message = "Are you sure you're not a zombie? You can enter max age: " + MAX_AGE;
            throw new RegistrationException(message);
        }
        if (user.getAge() < 0) {
            String message = "The age cannot be negative number";
            throw new RegistrationException(message);
        }
    }
}
