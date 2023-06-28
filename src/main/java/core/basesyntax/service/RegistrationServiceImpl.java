package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_SIZE_OF_STRING = 6;
    private static final int MIN_ALLOWED_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        registerNullUser(user);
        registerByNullLogin(user);
        registerByNullPassword(user);
        registerByNullAge(user);
        registerByAlreadyCreatedLogin(user);
        registerLoginLessThan6Chars(user);
        registerPasswordLessThan6Chars(user);
        registerByNotAllowedAge(user);
        return storageDao.add(user);
    }

    public void registerNullUser(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }
    }

    public void registerByNullLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login cannot be null");
        }
    }

    public void registerByNullPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password cannot be null");
        }
    }

    public void registerByNullAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age cannot be null");
        }
    }

    public void registerByAlreadyCreatedLogin(User user) {
        if (storageDao.get(user.getLogin()) != null
                && storageDao.get(user.getLogin()).getLogin() == user.getLogin()) {
            throw new InvalidDataException("This login has already been created");
        }
    }

    public void registerLoginLessThan6Chars(User user) {
        if (user.getLogin().length() < MIN_SIZE_OF_STRING) {
            throw new InvalidDataException("Login is less than 6 characters");
        }
    }

    public void registerPasswordLessThan6Chars(User user) {
        if (user.getPassword().length() < MIN_SIZE_OF_STRING) {
            throw new InvalidDataException("Password is less than 6 characters");
        }
    }

    public void registerByNotAllowedAge(User user) {
        if (user.getAge() < MIN_ALLOWED_AGE) {
            throw new InvalidDataException(
                    "Your are too young. You need to be at least 18 years old");
        }
    }
}
