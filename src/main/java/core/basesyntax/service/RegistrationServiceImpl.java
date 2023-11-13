package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static int MIN_AGE;
    private static int MIN_LOGIN_LENGTH;
    private static int MIN_PASSWORD_LENGTH;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        MIN_AGE = 18;
        MIN_LOGIN_LENGTH = 6;
        MIN_PASSWORD_LENGTH = 6;
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User object is null!");
        }

        String login = user.getLogin();

        checkLogin(login);
        checkAge(user.getAge());
        checkPassword(user.getPassword());

        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with login: " + login + " already exist");
        }

        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("User login can`t be null!");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Minimum login length is "
                + MIN_LOGIN_LENGTH
                + " characters. But your login <"
                + login + "> length is "
                + login.length() + "characters.");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("User age can`t be null!");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                + age + ". Min allowed age is "
                + MIN_AGE);
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("User password can`t be null!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Minimum password length is "
                + MIN_PASSWORD_LENGTH
                + " characters. But your password <"
                + password + "> length is "
                + password.length() + "characters.");
        }
    }
}
