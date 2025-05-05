package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new InvalidUserException("User can`t be null");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidUserException("User`s login must be not null");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidUserException("User with login " + login + " already exists.");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidUserException("User`s password must be not null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("User`s password length must be "
                    + MIN_PASSWORD_LENGTH + " or more.");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new InvalidUserException("User`s age must be not null");
        }
        if (age < MIN_AGE) {
            throw new InvalidUserException("User`s age must be more than " + MIN_AGE);
        }
    }
}
