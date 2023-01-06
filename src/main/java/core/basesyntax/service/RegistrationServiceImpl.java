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
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Invalid data. User login can't be null or empty");
        }
        if (storageDao.get(login) != null) {
            throw new ValidationException("Invalid data. User with this login already exists");
        }
    }

    private void checkAge(Integer age) {
        if (age == null || age < MINIMAL_VALID_AGE) {
            throw new ValidationException("Invalid data. You must be "
                    + MINIMAL_VALID_AGE + " years old to register");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new ValidationException("Invalid data. Your password can't be null");
        }
        if (password.length() < MINIMAL_VALID_PASSWORD_LENGTH) {
            throw new ValidationException("Invalid data. "
                    + "Your password should be at least "
                    + MINIMAL_VALID_PASSWORD_LENGTH + " characters");
        }
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null");
        }
    }
}
