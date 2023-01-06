package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user.getLogin());
        checkAge(user.getAge());
        checkPassword(user.getPassword());
        storageDao.add(user);
        return user;
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidUserDataException("Login can't be empty!");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidUserDataException("User with this login "
                    + login + " is already registered!");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidUserDataException("Password can't be empty!");
        }
        if (password.length() <= MIN_PASSWORD_SIZE) {
            throw new InvalidUserDataException("Password length can't be"
                     + "less then " + MIN_PASSWORD_SIZE + "!");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new InvalidUserDataException("Your age can't be null");
        }
        if (age < MIN_AGE) {
            throw new InvalidUserDataException("Your age " + age + " less"
                    + " then allowed: " + MIN_AGE);
        }
    }
}
