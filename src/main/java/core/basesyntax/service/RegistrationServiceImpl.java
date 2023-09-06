package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidDataException(
                    "Login less than " + MIN_LOGIN_LENGTH
            );
        }
        if (storageDao.get(login) != null) {
            throw new InvalidDataException("User with this login is already exists");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException(
                    "Password less than " + MIN_PASSWORD_LENGTH
            );
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new InvalidDataException(
                    "Sorry, min allowed age is " + MIN_AGE
            );
        }
    }
}
