package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USERS_AGE = 18;
    private static final int MAX_USERS_AGE = 150;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUsersData(user);
        return storageDao.add(user);
    }

    private void checkUsersData(User user) {
        if (user == null) {
            throw new RegistrationException("Input data is null.");
        }
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        checkLogin(user.getLogin());
    }

    private void checkAge(Integer age) {
        if (age == null
                || age < MIN_USERS_AGE
                || age > MAX_USERS_AGE) {
            throw new RegistrationException("User's age must be between "
                    + MIN_USERS_AGE + " and " + MAX_USERS_AGE + ".");
        }
    }

    private void checkPassword(String password) {
        if (password == null
                || password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length must be at least "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
    }

    private void checkLogin(String login) {
        if (login == null || storageDao.get(login) != null) {
            throw new RegistrationException("User with this login is already exists.");
        }
    }
}
