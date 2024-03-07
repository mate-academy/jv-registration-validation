package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 128;
    
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be NULL!");
        }
        checkUserInput(user);
        return storageDao.add(user);
    }

    private void checkUserInput(User user) {
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
    }

    private void checkAge(int age) {
        if (age < MIN_AGE) {
            throw new InvalidDataException(
                    "User age must be at least " + MIN_AGE
                    + " years, but was " + age + "!");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidDataException(
                    "The password should not be NULL!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException(
                    "The password must be at least " + MIN_PASSWORD_LENGTH
                    + " characters, but was " + password.length() + "!");
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidDataException(
                    "Login should not be NULL!");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidDataException("Login is already taken!");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException(
                    "Login must be at least " + MIN_LOGIN_LENGTH
                    + " characters, but was " + login.length() + "!");
        }
        if (login.length() > MAX_LOGIN_LENGTH) {
            throw new InvalidDataException(
                    "Login should not be longer than " + MAX_LOGIN_LENGTH
                    + " characters, but was " + login.length() + "!");
        }
    }
}
