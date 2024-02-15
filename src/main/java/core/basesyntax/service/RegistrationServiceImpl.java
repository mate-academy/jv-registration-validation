package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
        checkExistingUser(user.getLogin());

        storageDao.add(user);
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
        if (login.length() < 6) {
            throw new RegistrationException("Login is less than 6 characters");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RegistrationException("User's password is null");
        }
        if (password.length() < 6) {
            throw new RegistrationException("Password is less than 6 characters");
        }
    }

    private void checkAge(Integer age) {
        if (age == null) {
            throw new RegistrationException("User's age is null");
        }
        if (age < 18) {
            throw new RegistrationException("Age is under 18");
        }
    }

    private void checkExistingUser(String login) {
        if (storageDao.get(login) != null) {
            throw new RegistrationException("User with this login is already in the storage");
        }
    }
}
