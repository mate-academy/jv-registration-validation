package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final String INVALID_LOGIN_LENGTH = "Invalid login length!";
    private static final String INVALID_PASS_LENGTH = "Invalid password length!";
    private static final String INVALID_AGE = "Invalid age value!";
    private static final String NULL_LOGIN = "Login can't be null!";
    private static final String NULL_AGE = "Age can't be null!";
    private static final String NULL_PASS = "Password can't be null!";
    private static final String EXISTS_MSG = "User with this login already exists!";
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    public void validateUser(User user) {
        validateNotNull(user);
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        validateExistingUser(user.getLogin());
    }

    private void validateNotNull(User user) {
        if (user.getLogin() == null) {
            throw new InvalidUserException(NULL_LOGIN);
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException(NULL_PASS);
        }
        if (user.getAge() == null) {
            throw new InvalidUserException(NULL_AGE);
        }
    }

    private void validateLogin(String login) {
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException(INVALID_LOGIN_LENGTH);
        }
    }

    private void validatePassword(String password) {
        if (password.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException(INVALID_PASS_LENGTH);
        }
    }

    private void validateAge(int age) {
        if (age < MIN_AGE) {
            throw new InvalidUserException(INVALID_AGE);
        }
    }

    private void validateExistingUser(String login) {
        User existingUser = storageDao.get(login);
        if (existingUser != null) {
            throw new InvalidUserException(EXISTS_MSG);
        }
    }
}
