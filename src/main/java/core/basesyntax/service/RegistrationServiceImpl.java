package core.basesyntax.service;

import core.basesyntax.CustomRegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LENGTH = 6;
    private static final int ADULT = 18;
    private static final String NULL_USER = "Your user is null";
    private static final String SHORT_LOGIN = "Your login is too short, it should be more then "
            + MINIMUM_LENGTH + " characters!";
    private static final String SHOULD_BE_ADULT = "You must be 18 years old or older!";
    private static final String ALLOWED_PASSWORD = "Your password should be more then "
            + MINIMUM_LENGTH + " characters!";
    private static final String AGE_VALUE_NULL = "Your ageValue is null";
    private static final String PASSWORD_VALUE_NULL = "Your passwordValue is null";
    private static final String LOGIN_VALUE_NULL = "Your loginValue is null";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        valueIsNull(user);
        withLittlePutParameters(user);
        return storageDao.add(user);
    }

    private void withLittlePutParameters(User user) {
        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new CustomRegistrationException(SHORT_LOGIN);
        }
        if (user.getAge() < ADULT) {
            throw new CustomRegistrationException(SHOULD_BE_ADULT);
        }
        if (user.getPassword().length() < MINIMUM_LENGTH) {
            throw new CustomRegistrationException(ALLOWED_PASSWORD);
        }
    }

    private void valueIsNull(User user) {
        if (user == null) {
            throw new CustomRegistrationException(NULL_USER);
        }
        if (user.getAge() == null) {
            throw new CustomRegistrationException(AGE_VALUE_NULL);
        }
        if (user.getPassword() == null) {
            throw new CustomRegistrationException(PASSWORD_VALUE_NULL);
        }
        if (user.getLogin() == null) {
            throw new CustomRegistrationException(LOGIN_VALUE_NULL);
        }
    }


}
