package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
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
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        checkLoginNotExist(user.getLogin());

        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Not valid login: " + login
                    + ". Min login length is " + MIN_LOGIN_LENGTH);
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Not valid password length: " + password
                    + ". Min password length is " + MIN_PASSWORD_LENGTH);
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (age < 0) {
            throw new RegistrationException("Not valid age: " + age + ". Age can't be negative");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Not valid age: " + age
                    + ". Min allowed age is " + MIN_AGE);
        }
    }

    private void checkLoginNotExist(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with login '" + login + "' already exists");
        }
    }
}
