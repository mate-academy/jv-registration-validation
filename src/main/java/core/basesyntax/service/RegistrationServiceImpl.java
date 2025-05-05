package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.InvalidInputDataException;
import core.basesyntax.service.exception.UserAlreadyExistsException;

public class RegistrationServiceImpl implements RegistrationService {
    public static final String NULL_INPUT_DATA_MESSAGE =
            "User cannot be null";
    public static final String INVALID_LOGIN_MESSAGE =
            "User's login is at least 6 characters";
    public static final String INVALID_PASSWORD_MESSAGE =
            "User's password is at least 6 characters";
    public static final String INVALID_MIN_AGE_MESSAGE =
            "User's age is at least 18 years old";
    public static final String USER_ALREADY_EXISTS_MESSAGE =
            "User with the given login already exists";
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);

        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS_MESSAGE);
        }
        return storageDao.add(user);
    }

    private void validateUserData(User user) {
        if (user == null) {
            throw new InvalidInputDataException(NULL_INPUT_DATA_MESSAGE);
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
    }

    private void validateLogin(String login) {
        if (login == null || login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidInputDataException(INVALID_LOGIN_MESSAGE);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputDataException(INVALID_PASSWORD_MESSAGE);
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new InvalidInputDataException(INVALID_MIN_AGE_MESSAGE);
        }
    }
}
