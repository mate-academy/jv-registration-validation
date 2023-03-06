package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) throws ValidationException {
        return storageDao.add(verifyUser(user));
    }

    private User verifyUser(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null!");
        }
        verifyLogin(user.getLogin());
        verifyPassword(user.getPassword());
        verifyAge(user.getAge());
        return user;
    }

    private void verifyLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new ValidationException("Login can't be null or empty!");
        }
        if (storageDao.get(login) != null) {
            throw new ValidationException("User with login '" + login + "' already exist!");
        }
    }

    private void verifyPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new ValidationException("Password can't be null or less "
                    + MIN_PASSWORD_LENGTH + " symbols!");
        }
    }

    private void verifyAge(Integer age) {
        if (age == null || age < MIN_AGE) {
            throw new ValidationException("Age can't be null or less " + MIN_AGE + " year old!");
        }
    }
}
