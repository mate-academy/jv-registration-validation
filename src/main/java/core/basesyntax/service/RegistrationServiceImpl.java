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
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User is already registered: " + user.getLogin());
        }
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login can't be null!");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters long: " + login);
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can't be null!");
        }
        if (age <= MIN_AGE) {
            throw new RegistrationException("The minimum age is " + MIN_AGE);
        }

    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password can't be null!");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH);
        }
    }
}
