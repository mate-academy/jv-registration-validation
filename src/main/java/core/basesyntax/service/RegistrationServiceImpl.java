package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.jetbrains.annotations.NotNull;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new InvalidInputDataException("User can't be null");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidInputDataException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputDataException("User with this login is already exists!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidInputDataException("Password can't be null");
        }
        if (user.getPassword().length() <= MIN_PASSWORD_LENGTH) {
            throw new InvalidInputDataException("Password length must be more or equals " + MIN_PASSWORD_LENGTH);
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidInputDataException("Age can't be null");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new InvalidInputDataException("Age must be more or equals " + MIN_USER_AGE);
        }
    }
}

