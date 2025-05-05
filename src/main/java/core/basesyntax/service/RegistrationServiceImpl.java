package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_AND_PASSWORD_MINIMUM = 6;
    private static final int AGE_MINIMUM = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateAge(user.getAge());
        validatePassword(user.getPassword());
        validateLogin(user.getLogin());
        validateUniqueLogin(user.getLogin());
        return storageDao.add(user);
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new InvalidUserException("User's age can't be null");
        }
        if (age < AGE_MINIMUM) {
            throw new InvalidUserException("Invalid user's age: " + age
                    + ". Min allowed age is: " + AGE_MINIMUM);
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new InvalidUserException("User's password can't be null");
        }
        if (password.length() < LOGIN_AND_PASSWORD_MINIMUM) {
            throw new InvalidUserException("Invalid password: " + password
                    + ". Min allowed password length is: " + LOGIN_AND_PASSWORD_MINIMUM);
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new InvalidUserException("User's login can't be null");
        }
        if (login.length() < LOGIN_AND_PASSWORD_MINIMUM) {
            throw new InvalidUserException("Invalid login: " + login
                    + ". Min allowed login length is: " + LOGIN_AND_PASSWORD_MINIMUM);
        }
    }

    private void validateUniqueLogin(String login) {
        if (storageDao.get(login) != null) {
            throw new InvalidUserException("User already exists in the storage");
        }
    }
}
