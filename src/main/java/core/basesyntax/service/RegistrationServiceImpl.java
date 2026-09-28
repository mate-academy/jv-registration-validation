package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LEN = 6;
    private static final int MIN_PASSWORD_LEN = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    private void checkUser(User user) {
        String login = user.getLogin();

        if (login == null || login.length() < MIN_LOGIN_LEN) {
            throw new RegistrationException("Login must be at least 6 characters");
        }

        String password = user.getPassword();

        if (password == null || password.length() < MIN_PASSWORD_LEN) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LEN + " characters");
        }

        Integer age = user.getAge();

        if (age == null || age < MIN_AGE) {
            throw new RegistrationException("Age is too low");
        }

        User userFromStorage = this.storageDao.get(login);
        if (userFromStorage != null) {
            throw new RegistrationException("User is already registered");
        }
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }

        checkUser(user);

        return this.storageDao.add(user);
    }
}
