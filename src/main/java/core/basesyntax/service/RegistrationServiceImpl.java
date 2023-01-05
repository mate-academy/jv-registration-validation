package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        checkAvailability(user);
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidUserException("User login can't be null");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidUserException("User password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("User password must be more than 6 characters");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new InvalidUserException("Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new InvalidUserException("Age should be more or equal 18");
        }
    }

    private void checkAvailability(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("This user is registered");
        }
    }
}
