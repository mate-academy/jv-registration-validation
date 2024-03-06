package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidDataException {
        verify(user);
        storageDao.add(user);
        return user;
    }

    public void checkUsersInfo(User user) throws InvalidDataException {
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
    }

    public void checkAge(User user) throws InvalidDataException {
        if (user.getAge() == null) {
            throw new InvalidDataException("Age can't be null.");
        }
        if (user.getAge() < 18) {
            throw new InvalidDataException("Age must be at least 18 y.o.");
        }

    }

    private void checkLogin(User user) throws InvalidDataException {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null.");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidDataException("Short login length. "
                    + "It must be at least 6 characters length!");
        }
    }

    private void checkPassword(User user) throws InvalidDataException {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidDataException("Short password length. "
                    + "It must be at least 6 characters length!");
        }
    }

    private void verify(User user) throws InvalidDataException {
        if (user != null) {
            checkUsersInfo(user);
        }
    }
}
