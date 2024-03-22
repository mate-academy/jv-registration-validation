package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptionforservice.RegistrationException;
import core.basesyntax.model.User;

import javax.swing.*;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        isUserNull(user);
        isValidAge(user.getAge());
        isValidPassword(user.getPassword());
        isValidLogin(user.getLogin());
        isUserInStorage(user);
        return storageDao.add(user);
    }

    private void isUserNull(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null!");
        }
    }

    private void isValidAge(int age) {
        if (age < MIN_AGE) {
            throw new RegistrationException("Invalid login format "
                    + age + ". Age was less than should be " + MIN_AGE);
        }
    }

    private void isValidPassword (String password) {
        if (password == null) {
            throw new RegistrationException("Password null input. Try again!");
        }
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid login format "
                    + password + ". Min length password: " + MINIMUM_PASSWORD_LENGTH);
        }

    }

    private void isValidLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login null input.Try again!");
        }
        if (login.length() < MINIMUM_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid login format "
                    + login + ". Min length login : " + MINIMUM_LOGIN_LENGTH);
        }
    }

    private void isUserInStorage(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This user already exists");
        }
    }
}
