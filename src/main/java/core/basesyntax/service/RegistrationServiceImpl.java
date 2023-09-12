package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null!");
        }
        String login = user.getLogin();
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with given login \""
                    + login + "\" already exists");
        }
        validateLogin(login);
        validatePassword(user.getPassword());
        validateAge(user.getAge());

        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can`t be null");
        }
        int loginLength = login.length();
        if (loginLength < MINIMAL_LENGTH) {
            throw new RegistrationException("Login has to have at lest"
                    + MINIMAL_LENGTH + " characters, but was "
                    + loginLength);
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can`t be null");
        }
        int passwordLength = password.length();
        if (passwordLength < MINIMAL_LENGTH) {
            throw new RegistrationException("Password has to have at lest"
                    + MINIMAL_LENGTH + " characters, but was "
                    + passwordLength);
        }
    }

    private void validateAge(int age) {
        if (age < MINIMAL_AGE) {
            throw new RegistrationException("Minimal age allowed - "
                    + MINIMAL_AGE + ", but was " + age);
        }
    }
}
