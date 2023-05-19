package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user != null) {
            loginValidation(user.getLogin());
            passwordValidation(user.getPassword());
            ageValidation(user.getAge());
        } else {
            throw new InvalidInputException("User can't be null");
        }
    }

    private void loginValidation(String login) {
        if (login == null) {
            throw new InvalidInputException("Login can't be null");
        } else if (login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidInputException("Login must be at least 6 characters long");
        } else if (storageDao.get(login) != null) {
            throw new InvalidInputException("User with this login already exists");
        }
    }

    private void passwordValidation(String password) {
        if (password == null) {
            throw new InvalidInputException("Password can't be null");
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidInputException("Password must be at least 6 characters long");
        }
    }

    private void ageValidation(int age) {
        if (age < MIN_AGE) {
            throw new InvalidInputException("You must be at least 18 years old for register");
        }
    }
}
