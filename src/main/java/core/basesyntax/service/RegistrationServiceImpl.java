package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHAR_COUNT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        isUserValid(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        return storageDao.add(user);
    }

    private void validateLogin(String login) {
        if (login == null || login.isEmpty()) {
            throw new RegistrationException("Login can't be null");
        }
        if (login.length() < MIN_CHAR_COUNT) {
            throw new RegistrationException("Login is too short"
                    + login
                    + "Min char count is "
                    + MIN_CHAR_COUNT);
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new RegistrationException("Password can't be null"
                    + "Min char count is "
                    + MIN_CHAR_COUNT);
        }
        if (password.length() < MIN_CHAR_COUNT) {
            throw new RegistrationException("Password is too short"
                    + password
                    + "Min char count is "
                    + MIN_CHAR_COUNT);
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("Age can't be null"
                    + "Min age is "
                    + MIN_AGE);
        }
        if (age < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + age
                    + ". Min allowed age is "
                    + MIN_AGE);
        }
    }

    private void isUserValid(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
    }
}
