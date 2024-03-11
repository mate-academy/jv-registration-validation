package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkIfUserIsNull(user);
        checkLoginLength(user);
        checkIfUserExists(user);
        checkAgeLimit(user);
        checkPasswordLength(user);
        return storageDao.add(user);
    }

    private void checkIfUserIsNull(User user) {
        if (user == null) {
            throw new ValidationException("User is null ");
        }
    }

    private void checkIfUserExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new ValidationException("We already have such user - " + user.getLogin());
        }
    }

    private void checkAgeLimit(User user) {
        if (user.getAge() == null) {
            throw new ValidationException("Age is null");
        }
        if (user.getAge() != null && user.getAge() < MIN_AGE) {
            throw new ValidationException("Age is incorrect - " + user.getAge());
        }
    }

    private void checkPasswordLength(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Password is null");
        }
        if (user.getPassword() != null && user.getPassword().length() < MIN_LENGTH) {
            throw new ValidationException(
                    "Password shorter than " + MIN_LENGTH + " symbols - " + user.getPassword());
        }
    }

    private void checkLoginLength(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("Login is null");
        }
        if (user.getLogin() != null && user.getLogin().length() < MIN_LENGTH) {
            throw new ValidationException(
                    "Login shorter than " + MIN_LENGTH + " symbols - " + user.getLogin());
        }
    }
}
