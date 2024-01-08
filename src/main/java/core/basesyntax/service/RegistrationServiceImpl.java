package core.basesyntax.service;

import core.basesyntax.CustomRegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final String NULL_USER_MESSAGE = "Your user is null";
    private static final String SHORT_LOGIN_MESSAGE = "Your login is too short,"
            + " it should be more then "
            + MINIMUM_LOGIN_LENGTH + " characters!";
    private static final String LOGIN_VALUE_NULL_MESSAGE = "Your loginValue is null";
    private static final int MIN_AGE = 18;
    private static final String SHOULD_BE_ADULT_MESSAGE = "You must be 18 years old or older!";
    private static final String NOT_ALLOWED_PASSWORD_MESSAGE = "Your password should be more then "
            + MINIMUM_PASSWORD_LENGTH + " characters!";
    private static final String AGE_VALUE_NULL_MESSAGE = "Your ageValue is null";
    private static final String PASSWORD_VALUE_NULL_MESSAGE = "Your passwordValue is null";
    private static final String DUPLICATE_USER_MESSAGE = "User with this login already exists";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        validateNonNull(user);
        validateMinValues(user);
        validateIfUserAlreadyRegistered(user);
    }

    private void validateMinValues(User user) {
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new CustomRegistrationException(SHORT_LOGIN_MESSAGE);
        }
        if (user.getAge() < MIN_AGE) {
            throw new CustomRegistrationException(SHOULD_BE_ADULT_MESSAGE);
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new CustomRegistrationException(NOT_ALLOWED_PASSWORD_MESSAGE);
        }
    }

    private void validateNonNull(User user) {
        if (user == null) {
            throw new CustomRegistrationException(NULL_USER_MESSAGE);
        }
        if (user.getAge() == null) {
            throw new CustomRegistrationException(AGE_VALUE_NULL_MESSAGE);
        }
        if (user.getPassword() == null) {
            throw new CustomRegistrationException(PASSWORD_VALUE_NULL_MESSAGE);
        }
        if (user.getLogin() == null) {
            throw new CustomRegistrationException(LOGIN_VALUE_NULL_MESSAGE);
        }
    }

    private void validateIfUserAlreadyRegistered(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new CustomRegistrationException(DUPLICATE_USER_MESSAGE);
        }
    }
}
