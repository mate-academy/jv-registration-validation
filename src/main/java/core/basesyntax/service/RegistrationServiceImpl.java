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
        checkValidAge(user.getAge());
        checkValidLogin(user.getLogin());
        checkValidPassword(user.getPassword());
        if (storageDao.get(user.getLogin()) != null) {
            throw new NotValidDataException("User with this login already exists");
        }
        return storageDao.add(user);
    }

    private void checkValidLogin(String login) {
        if (login == null) {
            throw new NotValidDataException("Login can't be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new NotValidDataException("Login length must be at least"
                    + MIN_LOGIN_LENGTH);
        }
    }

    private void checkValidAge(Integer age) {
        if (age == null) {
            throw new NotValidDataException("Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new NotValidDataException("Age must be at least"
                    + MIN_AGE);
        }
    }

    private void checkValidPassword(String password) {
        if (password == null) {
            throw new NotValidDataException("Password can't be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new NotValidDataException("Password length must be at least"
                    + MIN_PASSWORD_LENGTH);
        }
    }
}
