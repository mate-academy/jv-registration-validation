package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        checkValidLogin(user.getLogin());
        checkValidPassword(user.getPassword());
        checkValidAge(user.getAge());
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }

    private void checkValidLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (login.trim().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
    }

    private void checkValidPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (password.trim().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
    }

    private void checkValidAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException(
                    "Not valid age: " + age + ". Min allowed age is " + MIN_AGE
            );
        }
    }
}
