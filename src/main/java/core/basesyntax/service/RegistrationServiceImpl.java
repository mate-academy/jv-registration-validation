package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 118;
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with such login already exists");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User must be at least " + MIN_AGE + " years old");
        }
        if (user.getAge() > MAX_AGE) {
            throw new InvalidDataException("Incorrect age. Please enter your real age");
        }
    }
}
