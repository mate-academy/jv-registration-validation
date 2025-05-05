package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_LENGTH = 6;
    private static final int PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists.");
        }

        storageDao.add(user);
        return user;
    }

    private void validateLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RegistrationException("Login cannot be null or empty");
        }
        if (login.length() < LOGIN_LENGTH) {
            throw new RegistrationException("Login must have at least "
                    + LOGIN_LENGTH + " characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password cannot be null or empty");
        }
        if (password.length() < PASSWORD_LENGTH) {
            throw new RegistrationException("Password must have at least "
                    + PASSWORD_LENGTH + " characters");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("User must be at least "
                    + MIN_AGE + " years old");
        }
    }
}
