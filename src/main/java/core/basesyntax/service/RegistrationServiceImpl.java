package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidAgeException;
import core.basesyntax.exceptions.InvalidLoginException;
import core.basesyntax.exceptions.InvalidPasswordException;
import core.basesyntax.exceptions.NullUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new NullUserException("User cannot be null");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
    }

    private void validateLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new InvalidLoginException("Login can't be null or empty!");
        }
        if (login.length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new InvalidLoginException("Login less than minimum length: "
                    + LOGIN_PASSWORD_MIN_LENGTH + " actual length: " + login.length());
        }
        if (storageDao.get(login) != null) {
            throw new InvalidLoginException("A user with this login already exists!");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InvalidPasswordException("Password can't be null or empty!");
        }
        if (password.length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new InvalidPasswordException("Password less than minimum length: "
                    + LOGIN_PASSWORD_MIN_LENGTH + " actual length: " + password.length());
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new InvalidAgeException("Age can't be null!");
        }
        if (age < MIN_AGE) {
            throw new InvalidAgeException("Age less than minimum: " + MIN_AGE);
        }
    }
}
