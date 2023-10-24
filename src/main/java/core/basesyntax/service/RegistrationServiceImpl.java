package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkValidNewUser(user);
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }

    private void checkValidNewUser(User newUser) {
        checkNotNullUser(newUser);
        String login = newUser.getLogin();
        String password = newUser.getPassword();
        Integer age = newUser.getAge();
        checkValidLogin(login);
        checkValidPassword(password);
        checkValidAge(age);
        checkNotReservedLogin(login);
    }

    private void checkNotNullUser(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
    }

    private void checkValidLogin(String login) {
        if (login == null || login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login should be least "
                    + MIN_LOGIN_LENGTH + "characters");
        }
    }

    private void checkValidPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password should be least "
                    + MIN_PASSWORD_LENGTH + "characters");
        }
    }

    private void checkValidAge(Integer age) {
        if (age == null || age < MIN_USER_AGE) {
            throw new RegistrationException("Age should be least " + MIN_USER_AGE);
        }
    }

    private void checkNotReservedLogin(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Login " + login + " is already used");
        }
    }
}
