package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LENGTH_OF_PASSWORD = 6;
    private static final int LENGTH_OF_LOGIN = 6;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserFieldsNotNull(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        validateLoginUnique(storageDao.get(user.getLogin()),user);
        return storageDao.add(user);
    }

    private void validateLoginUnique(User existingUser, User expected) {
        if (existingUser != null && existingUser.getLogin().equals(expected.getLogin())) {
            throw new RegistrationException("User with this login already registered!");
        }
    }

    private void validateLogin(String login) {
        if (login.length() < LENGTH_OF_LOGIN) {
            throw new RegistrationException("User login too short!");
        }
    }

    private void validateAge(int age) {
        if (age < MIN_AGE) {
            throw new RegistrationException("User too young!");
        }
        if (age > MAX_AGE) {
            throw new RegistrationException("User too old!");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < LENGTH_OF_PASSWORD) {
            throw new RegistrationException("User password too short!");
        }
    }

    private void validateUserFieldsNotNull(User userForCheck) {
        if (userForCheck.getLogin() == null) {
            throw new RegistrationException("Login cannot be null!");
        }
        if (userForCheck.getAge() == null) {
            throw new RegistrationException("Age cannot be null!");
        }
        if (userForCheck.getPassword() == null) {
            throw new RegistrationException("Password cannot be null!");
        }
    }
}

