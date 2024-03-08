package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

import java.util.Objects;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String USER_IS_NULL_ERROR_MESSAGE = "User can`t be null";
    private static final String USER_LOGIN_IS_NULL = "User login can`t be null";
    private static final String USER_LOGIN_ALREADY_EXISTS_ERROR_MESSAGE = "User with login %s already exists";
    private static final String USER_LOGIN_LENGTH_LESS_THAN_ERROR_MESSAGE = "User login %s length less then 6 characters";
    private static final String USER_PASSWORD_IS_NULL = "User password can`t be null";
    private static final String USER_PASSWORD_LENGTH_LESS_THAN_ERROR_MESSAGE = "User password %s length less then 6 characters";
    private static final String USER_AGE_LESS_THAN_ERROR_MESSAGE = "User age %d less then 18 years";
    private static final int LOGIN_PASSWORD_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIsUserValid(user);
        return storageDao.add(user);
    }

    private void checkIsUserValid(User user) {
        if (user == null) {
            throw new UserRegistrationException(USER_IS_NULL_ERROR_MESSAGE);
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException(USER_LOGIN_IS_NULL);
        }
        if (Objects.nonNull(storageDao.get(user.getLogin()))) {
            throw new UserRegistrationException(String.format(USER_LOGIN_ALREADY_EXISTS_ERROR_MESSAGE, user.getLogin()));
        }
        if (user.getLogin().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new UserRegistrationException(String.format(USER_LOGIN_LENGTH_LESS_THAN_ERROR_MESSAGE, user.getLogin()));
        }
        if (Objects.isNull(user.getPassword())) {
            throw new UserRegistrationException(USER_PASSWORD_IS_NULL);
        }
        if (user.getPassword().length() < LOGIN_PASSWORD_MIN_LENGTH) {
            throw new UserRegistrationException(String.format(USER_PASSWORD_LENGTH_LESS_THAN_ERROR_MESSAGE, user.getPassword()));
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new UserRegistrationException(String.format(USER_AGE_LESS_THAN_ERROR_MESSAGE, user.getAge()));
        }
    }
}
