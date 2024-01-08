package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_FIELD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final String NULL_FIELD_MESSAGE
            = "Login/password/age cannot be null";
    private static final String SHORT_LOGIN_MESSAGE
            = "Login should be at least %d characters";
    private static final String SHORT_PASSWORD_MESSAGE
            = "Password should be at least %d characters";
    private static final String LOW_AGE_MESSAGE
            = "User must be at least %d years old";
    private static final String EXISTING_USER_MESSAGE
            = "User with the same login already exists";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        checkExistingUser(user.getLogin());
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException(NULL_FIELD_MESSAGE);
        }
    }

    private void validateLogin(String login) {
        if (login.length() < MIN_FIELD_LENGTH) {
            throw new RegistrationException(String.format(SHORT_LOGIN_MESSAGE, MIN_FIELD_LENGTH));
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_FIELD_LENGTH) {
            throw new RegistrationException(String
                    .format(SHORT_PASSWORD_MESSAGE, MIN_FIELD_LENGTH));
        }
    }

    private void validateAge(int age) {
        if (age < MIN_AGE) {
            throw new RegistrationException(String.format(LOW_AGE_MESSAGE, MIN_AGE));
        }
    }

    private void checkExistingUser(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException(EXISTING_USER_MESSAGE);
        }
    }
}
