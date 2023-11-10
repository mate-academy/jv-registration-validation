package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User object is null!");
        }

        String login = user.getLogin();
        String password = user.getPassword();
        Integer age = user.getAge();

        if (age == null) {
            throw new RegistrationException("User age can`t be null!");
        }
        if (login == null) {
            throw new RegistrationException("User login can`t be null!");
        }
        if (password == null) {
            throw new RegistrationException("User password can`t be null!");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Minimum login length is "
                + MIN_LOGIN_LENGTH
                + " characters. But your login <"
                + login + "> length is "
                + login.length() + "characters.");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                + user.getAge() + ". Min allowed age is "
                + MIN_AGE);
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Minimum password length is "
                + MIN_PASSWORD_LENGTH
                + " characters. But your password <"
                + password + "> length is "
                + password.length() + "characters.");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with login: " + login + " already exist");
        }

        return storageDao.add(user);
    }
}
