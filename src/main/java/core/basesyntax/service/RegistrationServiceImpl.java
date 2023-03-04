package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 125;
    private static final int ZERO = 0;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidation(user);
        passwordValidation(user);
        loginValidation(user);
        ageValidation(user);
        storageDao.add(user);
        return user;
    }

    private void userValidation(User user) {
        if (user == null) {
            throw new RegistrationUserException("User can't be null");
        }
    }

    private void passwordValidation(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationUserException("Please write password:");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationUserException("Password is too short, must be minimum 6 symbol");
        }
    }

    private void loginValidation(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationUserException("Login can't be null or empty");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationUserException("Login: " + user.getLogin() + " is not available");
        }
    }

    private void ageValidation(User user) {
        if (user.getAge() == null) {
            throw new RegistrationUserException("Please write your age:");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationUserException("You are too young!");
        }
        if (user.getAge() > MAX_AGE || user.getAge() < ZERO) {
            throw new RegistrationUserException("Incorrect age!");
        }

    }
}
