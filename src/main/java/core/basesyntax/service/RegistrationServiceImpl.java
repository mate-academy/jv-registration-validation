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
        if (user.getLogin() == null) {
            throw new InvalidUserException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new InvalidUserException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_NUMBER_OF_SYMBOLS) {
            throw new InvalidUserException("Password can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("Invalid age");
        }
        if (isLoginExists(user)) {
            throw new InvalidUserException("This login already exists");
        }
        return storageDao.add(user);
    }

    private boolean isLoginExists(User user) {
        return storageDao.get(user.getLogin()) != null;
    }
}
