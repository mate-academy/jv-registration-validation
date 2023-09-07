package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.RegisterException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_LENGTH = 6;
    private static final int MIN_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegisterException("Null-user can't be registered.");
        }
        userExists(user.getLogin());
        loginIsValid(user.getLogin());
        passwordIsValid(user.getPassword());
        ageIsValid(user.getAge());
        return storageDao.add(user);
    }

    private void userExists(String login) {
        if (storageDao.get(login) != null) {
            throw new RegisterException("User with login ["
                    + login + "] already exists");
        }
    }

    private void loginIsValid(String login) {
        if (login == null) {
            throw new RegisterException("Login can't be null.");
        }
        if (login.length() < MIN_VALID_LENGTH) {
            throw new RegisterException("Login must have more then "
                    + MIN_VALID_LENGTH + " characters.");
        }
    }

    private void passwordIsValid(String password) {
        if (password == null) {
            throw new RegisterException("Password can't be null.");
        }
        if (password.length() < MIN_VALID_LENGTH) {
            throw new RegisterException("Password must have more then "
                    + MIN_VALID_LENGTH + " characters.");
        }
    }

    private void ageIsValid(Integer age) {
        if (age == null) {
            throw new RegisterException("Age can't be null.");
        }
        if (age < MIN_VALID_AGE) {
            throw new RegisterException("Age must be more " + MIN_VALID_AGE);
        }
    }
}
