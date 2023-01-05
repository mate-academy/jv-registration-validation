package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 3;
    private static final int LOGIN_MAX_LENGTH = 20;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 25;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidUserDataException("Login should not be null.");
        }
        if (login.isEmpty()) {
            throw new InvalidUserDataException("Login should not be empty.");
        }
        if (login.isBlank()) {
            throw new InvalidUserDataException("Login should not be blank.");
        }
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new InvalidUserDataException("User login should be at least " + LOGIN_MIN_LENGTH
                    + " characters.");
        }
        if (login.length() > LOGIN_MAX_LENGTH) {
            throw new InvalidUserDataException("User login should not be more than "
                    + LOGIN_MAX_LENGTH + " characters.");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidUserDataException("User with login " + login
                    + " is already registered.");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidUserDataException("Password should not be null.");
        }
        if (password.isEmpty()) {
            throw new InvalidUserDataException("Password should not be empty.");
        }
        if (password.isBlank()) {
            throw new InvalidUserDataException("Password should not be blank.");
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidUserDataException("Password should be at least "
                    + PASSWORD_MIN_LENGTH + " characters.");
        }
        if (password.length() > PASSWORD_MAX_LENGTH) {
            throw new InvalidUserDataException("Password should not be more than "
                    + PASSWORD_MAX_LENGTH + " characters.");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new InvalidUserDataException("User age should not be null.");
        }
        if (age < MIN_AGE) {
            throw new InvalidUserDataException("User age should not be less than min age.");
        }
    }
}
