package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int PASSWORD_LENGTH = 6;
    private static final int LOGIN_LENGTH = 6;
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
            throw new RegistrationException("This login is already assigned!");
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

    private void validateAge(int age) {
        if (age < MIN_AGE) {
            throw new RegistrationException("This user is so young!");
        }
        if (age > MAX_AGE) {
            throw new RegistrationException("This user is old!");
        }
    }

    private void validateLogin(String login) {
        if (login.length() < LOGIN_LENGTH) {
            throw new RegistrationException("This login is short!");
        }
    }

    private void validatePassword(String password) {
        if (password.length() < PASSWORD_LENGTH) {
            throw new RegistrationException("This password is short!");
        }
    }
}
