package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String USER_CANNOT_BE_NULL =
            "User cannot be null";
    private static final String LOGIN_CANNOT_BE_NULL =
            "Login can't be null";
    private static final String PASSWORD_CANNOT_BE_NULL =
            "Password can't be null";
    private static final String AGE_CANNOT_BE_NULL =
            "Age can't be null";
    private static final String USER_ALREADY_REGISTERED =
            "The user is already registered. Login: ";
    private static final String LOGIN_LENGTH_ERROR =
            "Login can't be less than " + LOGIN_MIN_LENGTH + " characters";
    private static final String PASSWORD_LENGTH_ERROR =
            "Password can't be less than " + PASSWORD_MIN_LENGTH + " characters";
    private static final String AGE_ERROR =
            "Age can't be less than " + MIN_AGE;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException(USER_CANNOT_BE_NULL);
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        checkIfUserIsAlreadyRegistered(user.getLogin());
        return storageDao.add(user);
    }

    private void checkIfUserIsAlreadyRegistered(String login) {
        if (storageDao.get(login) != null) {
            throw new InvalidUserDataException(USER_ALREADY_REGISTERED + login);
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new InvalidUserDataException(LOGIN_CANNOT_BE_NULL);
        }
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new InvalidUserDataException(LOGIN_LENGTH_ERROR);
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new InvalidUserDataException(PASSWORD_CANNOT_BE_NULL);
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new InvalidUserDataException(PASSWORD_LENGTH_ERROR);
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new InvalidUserDataException(AGE_CANNOT_BE_NULL);
        }
        if (age < MIN_AGE) {
            throw new InvalidUserDataException(AGE_ERROR);
        }
    }
}
