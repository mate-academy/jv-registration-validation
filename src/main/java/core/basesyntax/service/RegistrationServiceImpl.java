package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.security.InvalidParameterException;
import java.util.InputMismatchException;

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
            throw new IllegalArgumentException("Please enter your login."
                    + "Login field should not be null");
        }
        if (login.isEmpty()) {
            throw new IllegalArgumentException("Please enter your login."
                    + "Login field should not be empty");
        }
        if (storageDao.get(login) != null) {
            throw new IllegalArgumentException("Provided username already in use."
                    + "Please create a new login");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Please create your password."
                    + "Password field should not be null");
        }
        if (password.isEmpty()) {
            throw new IllegalArgumentException("Please create your password."
                    + "Password field should not be empty");
        }
        if (password.length() < MIN_LENGTH_PASSWORD) {
            throw new InputMismatchException("Your password must be at least 6 characters long");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new InvalidParameterException("The Age field can't be null");
        }
        if (age < 18) {
            throw new InvalidParameterException("users over the age of "
                    + VALID_AGE_FROM + " can only registered");
        }
    }
}
