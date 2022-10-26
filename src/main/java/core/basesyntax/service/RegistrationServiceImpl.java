package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exseption.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_NUMBER_OF_SYMBOLS = 6;
    private StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        checkIsLoginInvalid(user);
        checkIsPasswordInvalid(user);
        checkIsAgeInvalid(user);
        return storageDao.add(user);
    }

    private void checkIsLoginInvalid(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("This login already exists");
        }
    }

    private void checkIsAgeInvalid(User user) {
        if (user.getAge() == null) {
            throw new InvalidUserException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("Invalid age");
        }
    }

    private void checkIsPasswordInvalid(User user) {
        if (user.getPassword() == null) {
            throw new InvalidUserException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_NUMBER_OF_SYMBOLS) {
            throw new InvalidUserException("Password can't be null");
        }
    }
}
