package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null.");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login "
                    + user.getLogin() + " already exists.");
        }
        storageDao.add(user);
        return user;
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new RegistrationException("Login cannot be null.");
        }
        if (login.length() < MIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LENGTH + " characters long.");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new RegistrationException("Password cannot be null.");
        }
        if (password.length() < MIN_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_LENGTH + " characters long.");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age cannot be null.");
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("User must be at least " + MIN_AGE + " years old.");
        }
    }
}
