package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        checkLogin(user.getLogin());
        checkUserIsNotExist(user.getLogin());
        checkUserAge(user.getAge());
        checkUserPassword(user.getPassword());
        return storageDao.add(user);
    }

    private boolean checkLogin(String login) {
        if (login == null) {
            throw new InvalidDataException("Login can't be null");
        }
        if (login.length() < MIN_LENGTH) {
            throw new InvalidDataException("Login can't be  less than 6 characters");
        }
        return true;
    }

    private boolean checkUser(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        return true;
    }

    private boolean checkUserIsNotExist(String login) {
        if (storageDao.get(login) != null) {
            throw new InvalidDataException("User with login " + login
                    + " already exists");
        }
        return true;
    }

    private boolean checkUserAge(Integer age) {
        if (age == null) {
            throw new InvalidDataException("Age can't be null");
        }
        if (age < MIN_AGE) {
            throw new InvalidDataException("Age can't be less than 18");
        }
        return true;
    }

    private boolean checkUserPassword(String password) {
        if (password == null) {
            throw new InvalidDataException("Password can't be null");
        }
        if (password.length() < MIN_LENGTH) {
            throw new InvalidDataException("Password's length can't be less than 6 characters");
        }
        return true;
    }
}
