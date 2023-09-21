package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    @Override
    public User register(User user) {
        validateUser(user);
        checkExistingUser(user.getLogin());

        return storageDao.add(user);
    }

    private void validateUser(User user) {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
    }

    private void validateLogin(String login) {
        if (login == null || login.length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters long.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters long.");
        }
    }

    private void validateAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new RegistrationException("Age must be at least 18.");
        }
    }

    private void checkExistingUser(String login) {
        User existingUser = storageDao.get(login);
        if (existingUser != null) {
            throw new RegistrationException("User with this login already exists.");
        }
    }
}
