package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MAX_AGE = 120;
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidator(user);
        return storageDao.add(user);
    }

    private void userValidator(User user) {
        passValidator(user.getPassword());
        ageValidator(user.getAge());
        loginValidator(user.getLogin());
    }

    private void passValidator(String pass) {
        if (pass == null) {
            throw new InvalidUserException("Password is null");
        }
        if (pass.length() < MIN_LENGTH) {
            throw new InvalidUserException("Password is too short");
        }
    }

    private void ageValidator(int age) {
        if (age < 0) {
            throw new InvalidUserException("Negative age");
        }
        if (age < MIN_AGE) {
            throw new InvalidUserException("Illegal age");
        }
        if (age > MAX_AGE) {
            throw new InvalidUserException("Too old");
        }
    }

    private void loginValidator(String login) {
        if (login == null) {
            throw new InvalidUserException("Login is null");
        }
        if (login.length() < MIN_LENGTH) {
            throw new InvalidUserException("Login is too short");
        }
        if (storageDao.get(login) != null) {
            throw new InvalidUserException("Login is already taken");
        }
    }
}
