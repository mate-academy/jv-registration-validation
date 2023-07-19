package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 150;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new UserRegistrationException("User can`t be null");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new UserRegistrationException("Login can`t be null");
        }
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new UserRegistrationException("Login " + login + " with length " + login.length()
                    + " is invalid by min length " + LOGIN_MIN_LENGTH);
        }
        if (storageDao.get(login) != null) {
            throw new UserRegistrationException("This login already exist - " + login);
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new UserRegistrationException("Login can`t be null");
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new UserRegistrationException(password.length()
                    + " password length is invalid for min length " + PASSWORD_MIN_LENGTH);
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new UserRegistrationException("Login can`t be null");
        }
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new UserRegistrationException(age
                    + " years is out of bounds of " + MIN_AGE + " - " + MAX_AGE);
        }
    }
}
