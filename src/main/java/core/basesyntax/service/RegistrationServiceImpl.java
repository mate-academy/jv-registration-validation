package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can`t be null");
        }
        if (login.isBlank() || login.length() < MIN_LENGTH) {
            throw new RegistrationException("Login must have " + MIN_LENGTH + "or more symbols");
        }
        if (storageDao.get(login) != null) {
            throw new RegistrationException("Login is almost exit");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can`t be null");
        }
        if (password.isBlank() || password.length() < MIN_LENGTH) {
            throw new RegistrationException("Password must have " + MIN_LENGTH + "or more symbols");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can`t be null");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Age can`t be less than " + MIN_AGE);
        }
    }
}
