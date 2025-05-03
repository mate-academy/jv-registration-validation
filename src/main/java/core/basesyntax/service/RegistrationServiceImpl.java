package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE_VALUE = 18;

    @Override
    public User register(User user) {
        validateUser(user);
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        checkExistingUser(user.getLogin());

        STORAGE_DAO.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
    }

    private void checkLogin(String login) {
        if (login == null) {
            throw new RegistrationException("User's login is null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login is less than "
                    + MIN_LOGIN_LENGTH + " characters");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("User's password is null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("User's age is null");
        }
        if (age < MIN_AGE_VALUE) {
            throw new RegistrationException("Age is under " + MIN_AGE_VALUE);
        }
    }

    private void checkExistingUser(String login) {
        if (STORAGE_DAO.get(login) != null) {
            throw new RegistrationException("User with this login is already in the storage");
        }
    }
}
