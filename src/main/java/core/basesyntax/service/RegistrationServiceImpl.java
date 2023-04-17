package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.security.InvalidParameterException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int VALID_AGE_FROM = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
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
            throw new NullPointerException("Login field should not be null.");
        }
        if (login.isEmpty()) {
            throw new NullPointerException("Login field should not be empty.");
        }
        if (storageDao.get(login) != null) {
            throw new RuntimeException("Login like this already exist.");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new NullPointerException("Password field should not be null.");
        }
        if (password.isEmpty()) {
            throw new NullPointerException("Password field should not be empty.");
        }
        if (password.length() < MIN_LENGTH_PASSWORD) {
            throw new RuntimeException("Minimum password length must be "
                    + MIN_LENGTH_PASSWORD + " characters.");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new NullPointerException("Age can`t be null.");
        }
        if (age < VALID_AGE_FROM) {
            throw new InvalidParameterException("Only users over the age of "
                    + VALID_AGE_FROM + " can be registered.");
        }
    }
}
