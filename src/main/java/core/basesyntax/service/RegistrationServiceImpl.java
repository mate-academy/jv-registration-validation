package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_LOGIN_LENGTH = 6;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private static final int VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (isLoginInvalid(user) || isPasswordInvalid(user) || isAgeInvalid(user)) {
            throw new ValidationException("Validation error");
        }
    }

    private boolean isLoginInvalid(User user) {
        return user.getLogin() == null || user.getLogin().length() < VALID_LOGIN_LENGTH;
    }

    private boolean isPasswordInvalid(User user) {
        return user.getPassword() == null || user.getPassword().length() < VALID_PASSWORD_LENGTH;
    }

    private boolean isAgeInvalid(User user) {
        return user.getAge() < VALID_AGE;
    }
}
