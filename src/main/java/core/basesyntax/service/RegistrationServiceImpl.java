package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_PASSWORD_LENGTH = 6;
    private static final int MIN_REQUIRED_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Null user is not supported!");
        }
        loginValidate(user.getLogin());
        passwordValidate(user.getPassword());
        ageValidate(user.getAge());
        return storageDao.add(user);
    }

    private void loginValidate(String login) {
        if (login == null) {
            throw new RegistrationException("Null login is not supported!");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with login " + "\"" + login + "\""
                    + " is already registered");
        }
        if (login.length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationException("\"" + login
                    + "\"" + " login is less than " + MIN_LOGIN_PASSWORD_LENGTH + " characters");
        }
    }

    private void passwordValidate(String password) {
        if (password == null) {
            throw new RegistrationException("Null password is not supported!");
        }
        if (password.length() < MIN_LOGIN_PASSWORD_LENGTH) {
            throw new RegistrationException("\"" + password + "\""
                    + " password is less than " + MIN_LOGIN_PASSWORD_LENGTH + " characters");
        }
    }

    private void ageValidate(Integer age) {
        if (age == null) {
            throw new RegistrationException("Null age is not supported!");
        }
        if (age < MIN_REQUIRED_AGE) {
            throw new RegistrationException(age + " years old is less than"
                    + MIN_REQUIRED_AGE + "years old");
        }
    }
}
