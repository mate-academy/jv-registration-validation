package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_MIN_CHARACTERS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new ValidationException("Input User is null");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new ValidationException("User age is null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new ValidationException("Not valid age: "
                    + user.getAge()
                    + ". Min allowed age is "
                    + MIN_AGE);
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new ValidationException("Password can't be null");
        }
        if (user.getPassword().length() < DEFAULT_MIN_CHARACTERS) {
            throw new ValidationException("Min allowed password length is "
                    + DEFAULT_MIN_CHARACTERS);
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new ValidationException("Login can't be null");
        }
        if (user.getLogin().length() < DEFAULT_MIN_CHARACTERS) {
            throw new ValidationException("Min allowed login length is "
                    + DEFAULT_MIN_CHARACTERS);
        }
        User userInStorage = storageDao.get(user.getLogin());
        if (userInStorage != null) {
            throw new ValidationException("Same login was already registered, please try again");
        }

    }
}
