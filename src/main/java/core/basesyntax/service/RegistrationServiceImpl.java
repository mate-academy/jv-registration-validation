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
        if (user.getLogin() == null) {
            throw new ValidationException("Invalid login: cannot be null or empty.");
        }

        if (user.getPassword() == null) {
            throw new ValidationException("Invalid password: cannot be null or empty.");
        }

        if (user.getLogin().length() < VALID_LOGIN_LENGTH) {
            throw new ValidationException("Invalid login: must be at least 6 characters long.");
        }

        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new ValidationException("Invalid password: must be at least 6 characters long.");
        }

        if (user.getAge() < VALID_AGE) {
            throw new ValidationException("Invalid age: must be at least 18 years old.");
        }
    }
}
