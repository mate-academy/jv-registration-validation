package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfUserIsNull(user);
        checkIfUserExists(user);
        checkIfLoginIsValid(user.getLogin());
        checkIfPasswordIsValid(user.getPassword());
        checkIfAgeIsValid(user.getAge());
        return storageDao.add(user);
    }

    private void checkIfUserIsNull(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
    }

    private void checkIfUserExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login already exists");
        }
    }

    private void checkIfLoginIsValid(String login) {
        if (login == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (login.length() < LOGIN_MIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + LOGIN_MIN_LENGTH + " characters");
        }
    }

    private void checkIfPasswordIsValid(String password) {
        if (password == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + PASSWORD_MIN_LENGTH + " characters");
        }
    }

    private void checkIfAgeIsValid(int age) {
        if (age < MIN_AGE) {
            throw new RegistrationException("Age must be at least " + MIN_AGE);
        }
    }
}
