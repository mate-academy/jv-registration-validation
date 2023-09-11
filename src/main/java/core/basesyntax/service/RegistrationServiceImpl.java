package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.NotValidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new NotValidDataException("User can't be null");
        }
        checkValidAge(user);
        checkValidLogin(user);
        checkValidPassword(user);
        checkLoginUnused(user);
        return storageDao.add(user);
    }

    private void checkValidLogin(User user) {
        if (user.getLogin() == null) {
            throw new NotValidDataException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new NotValidDataException("Login length must be at least "
                    + String.format("%d", MIN_LOGIN_LENGTH));
        }
    }

    private void checkValidAge(User user) {
        if (user.getAge() == null) {
            throw new NotValidDataException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new NotValidDataException("Age must be at least "
                    + String.format("%d", MIN_AGE));
        }
    }

    private void checkValidPassword(User user) {
        if (user.getPassword() == null) {
            throw new NotValidDataException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new NotValidDataException("Password length must be at least "
                    + String.format("%d", MIN_PASSWORD_LENGTH));
        }
    }

    private void checkLoginUnused(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new NotValidDataException("User with this login already exists");
        }
    }
}
