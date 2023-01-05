package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_VALID_AGE = 18;
    private static final int MINIMAL_VALID_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user.getLogin());
        checkAge(user);
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (storageDao.get(login) != null) {
            throw new ValidationException("Invalid data. User with this login already exists");
        } else if (login == null || login.length() == 0) {
            throw new ValidationException("Invalid data. User login can't be null");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null || user.getAge() < MINIMAL_VALID_AGE) {
            throw new ValidationException("Invalid data. You must be 18 years old to register");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new ValidationException("Invalid data. Your password can't be null");
        } else if (password.length() < MINIMAL_VALID_PASSWORD_LENGTH) {
            throw new ValidationException("Invalid data. "
                    + "Your password should be at least 6 characters");
        }
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null");
        }
    }
}
