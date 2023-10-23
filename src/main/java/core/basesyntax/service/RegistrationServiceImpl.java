package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("The user is already registered. Login: "
                            + user.getLogin());
        }
        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new InvalidUserDataException("Login can't be null");
        }
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new InvalidUserDataException("Login can't be less than "
                    + LOGIN_MIN_LENGTH + " characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new InvalidUserDataException("Password can't be null");
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidUserDataException("Password can't be less than "
                    + PASSWORD_MIN_LENGTH + " characters");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new InvalidUserDataException("Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new InvalidUserDataException("Age can't be less then " + MIN_AGE);
        }
    }
}
