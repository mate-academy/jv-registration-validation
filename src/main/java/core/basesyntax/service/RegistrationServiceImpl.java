package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkElementForNull(user);
        String login = user.getLogin();
        checkLoginValidation(login);
        checkUserLoginUniqueness(login);
        checkPasswordValidation(user.getPassword());
        checkAgeValidation(user.getAge());
        storageDao.add(user);
        return user;
    }

    private void checkAgeValidation(int age) {
        if (age < 18) {
            throw new InvalidUserException("User age cannot must be > 18");
        }
    }

    private void checkPasswordValidation(String password) {
        if (password.length() < 6) {
            throw new InvalidUserException("User password length must be > 6");
        }
    }

    private void checkLoginValidation(String login) {
        if (login.length() < 6) {
            throw new InvalidUserException("User login length must be > 6");
        }
    }

    private void checkUserLoginUniqueness(String login) {
        if (storageDao.get(login) != null) {
            throw new InvalidUserException("User with such login already exists");
        }
    }

    private void checkElementForNull(User user) {
        if (user == null) {
            throw new NullPointerException("User is null");
        }
        if (user.getLogin() == null) {
            throw new NullPointerException("Login is null");
        }
        if (user.getPassword() == null) {
            throw new NullPointerException("Password is null");
        }
        if (user.getAge() == null) {
            throw new NullPointerException("Age is null");
        }
    }
}
