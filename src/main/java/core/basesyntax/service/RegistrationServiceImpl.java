package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        checkIfUserNull(user);
        checkIfUserExists(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidValidationException("User's age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidValidationException("User must be older than " + MIN_AGE);
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidValidationException("User's login cannot be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidValidationException("User login must be at least  "
                    + MIN_LOGIN_LENGTH + " characters");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidValidationException("User's password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidValidationException("User password must be at least  "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
    }

    private void checkIfUserExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidValidationException("User already exists");
        }
    }

    private void checkIfUserNull(User user) {
        if (user == null) {
            throw new InvalidValidationException("User cannot be null");
        }
    }
}
