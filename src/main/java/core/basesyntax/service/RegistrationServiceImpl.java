package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("User can't be null");
        }

        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());

        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        throw new ValidationException("User already exist in database!");
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new ValidationException("Login can't be null");
        }

        if (login.length() < MIN_LENGTH) {
            throw new ValidationException("Minimum allowed login length is " + MIN_LENGTH);
        }
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new ValidationException("Password can't be null");
        }

        if (password.length() < MIN_LENGTH) {
            throw new ValidationException("Minimum allowed password length is " + MIN_LENGTH);
        }
    }

    private void validateAge(Integer age) {
        if (age == null) {
            throw new ValidationException("Age can't be null");
        }

        if (age < MIN_AGE) {
            throw new ValidationException("Minimum allowed age is " + MIN_AGE);
        }
    }
}
