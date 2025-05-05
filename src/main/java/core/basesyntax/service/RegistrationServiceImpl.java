package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void checkLogin(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        if (login == null) {
            throw new RegistrationException("Login can`t be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login length is less then "
                    + MIN_LOGIN_LENGTH + " symbols");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length is less then "
                    + MIN_PASSWORD_LENGTH + " symbols");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can`t be null");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Only users over the age of "
                    + MIN_AGE + " are allowed");
        }
    }
}
