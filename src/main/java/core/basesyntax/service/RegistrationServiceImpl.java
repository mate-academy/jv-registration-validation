package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_LOGIN_LENGTH = 6;
    private static final int VALID_PASSWORD_LENGTH = 6;
    private static final int VALID_AGE = 18;
    private static final long ID = 111232;
    private final StorageDao storageDao = new StorageDaoImpl();

    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        checkLoginExists(user);
        checkLoginNull(user);
        checkPasswordNull(user);
        checkPasswordInvalid(user);
        checkLoginInvalid(user);
        chackAgeInvalid(user);
    }

    private void checkLoginInvalid(User user) {
        if (user.getLogin().length() < VALID_LOGIN_LENGTH) {
            throw new ValidationException("Invalid login: must be at least "
                    + VALID_LOGIN_LENGTH + " characters long.");
        }
    }

    private void checkPasswordInvalid(User user) {
        if (user.getPassword().length() < VALID_PASSWORD_LENGTH) {
            throw new ValidationException("Invalid password: must be at least "
                    + VALID_PASSWORD_LENGTH + " characters long.");
        }
    }

    private void checkPasswordNull(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Invalid password: cannot be null or empty.");
        }
    }

    private void checkLoginNull(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("Invalid login: cannot be null or empty.");
        }
    }

    private void chackAgeInvalid(User user) {
        if (user.getAge() < VALID_AGE) {
            throw new ValidationException("Invalid age: must be at least "
                    + VALID_AGE + " years old.");
        }
    }

    public void checkLoginExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("Invalid login: this login is already exists");
        }
    }
}
