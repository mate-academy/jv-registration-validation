package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int AGE_ATLEAST = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verify(user);
        storageDao.add(user);
        return user;
    }

    private void checkAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null.");
        }
        if (user.getAge() < AGE_ATLEAST) {
            throw new InvalidDataException("Age must be at least 18 y.o.");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null.");
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new InvalidDataException("Short login length. "
                    + "It must be at least 6 characters length!");
        }
    }

    private void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new InvalidDataException("Short password length. "
                    + "It must be at least 6 characters length!");
        }
    }

    private void verify(User user) {
        if (user != null && storageDao.get(user.getLogin()) != user) {
            checkAge(user);
            checkLogin(user);
            checkPassword(user);
        } else {
            throw new InvalidDataException("User exists.");
        }
    }
}
