package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User field can't be empty.");
        }
        checkUserLogin(user.getLogin());
        checkUserPassword(user.getPassword());
        checkUserAge(user.getAge());
        return storageDao.add(user);
    }

    private void checkUserLogin(String login) {
        if (login == null) {
            throw new InvalidUserException("Login field is null.");
        }
        if (login.isEmpty()) {
            throw new InvalidUserException("Login field can't be empty.");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidUserException("Login: " + login
                    + " is already exist, please choose another login.");
        }
    }

    private void checkUserPassword(String password) {
        if (password == null) {
            throw new InvalidUserException("Password field is null.");
        }
        if (password.contains(password.toUpperCase())) {
            throw new InvalidUserException("The password cannot contain only uppercase letters");
        }
        if (password.contains(password.toLowerCase())) {
            throw new InvalidUserException("The password cannot contain only lowercase letters");
        }
        if (password.isEmpty()) {
            throw new InvalidUserException("Password field can't be empty.");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("The password length must be minimum " + MIN_PASSWORD_LENGTH
                    + " characters, and your password length is : " + password.length());
        }
    }

    private void checkUserAge(Integer age) {
        if (age == null) {
            throw new InvalidUserException("Age field is null.");
        }
        if (age < MIN_USER_AGE) {
            throw new InvalidUserException("To register ,your age must be at least: "
                    + MIN_USER_AGE + " ,instead, your age is: " + age);
        }
    }
}
