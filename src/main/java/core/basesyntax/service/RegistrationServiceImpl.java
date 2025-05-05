package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validatePresence(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        validateUniqueness(user);
        storageDao.add(user);
        return user;
    }

    private void validatePresence(User user) {
        if (user == null) {
            throw new RegistrationException("User must be present");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login is required");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException(
                    String.format("Login length must be at least %d characters",
                            MIN_LOGIN_LENGTH)
            );
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password is required");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    String.format("Password length must be at least %d characters",
                            MIN_PASSWORD_LENGTH)
            );
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age is required");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(String.format("Minimum age is %d",
                    MIN_AGE));
        }
    }

    private void validateUniqueness(User user) {
        User storageUser = storageDao.get(user.getLogin());
        if (storageUser != null) {
            throw new RegistrationException("User already exists");
        }
    }
}
