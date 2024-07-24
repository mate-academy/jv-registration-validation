package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.model.ValidationException;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        checkUserExists(user.getLogin());
        storageDao.add(user);
        return user;
    }

    private void checkUserExists(String login) {
        if (storageDao.get(login) != null) {
            throw new ValidationException("User with that login has already exists.Try new login!");
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new ValidationException("Login cannot be null!");
        }
        if (login.length() < MIN_CHARACTERS) {
            throw new ValidationException("Login cannot be less than 6 characters!");
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new ValidationException("Password cannot be null!");
        }
        if (password.length() < MIN_CHARACTERS) {
            throw new ValidationException("Password cannot be less than 6 characters!");
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new ValidationException("Age cannot be null!");
        }
        if (age < MIN_AGE) {
            throw new ValidationException("Age cannot be less than 18!");
        }
    }
}
