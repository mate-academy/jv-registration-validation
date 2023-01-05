package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 120;
    private static final int MIN_CHARACTERS_NUMBER = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new InvalidInputDataException("Login can't be null");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidInputDataException("User with this login is already registered");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new InvalidInputDataException("Password can't be null");
        }
        if (password.length() < MIN_CHARACTERS_NUMBER) {
            throw new InvalidInputDataException("Password's length is less then 6 characters");
        }
    }

    private void checkAge(int age) {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new InvalidInputDataException("Not valid age");
        }
    }
}
