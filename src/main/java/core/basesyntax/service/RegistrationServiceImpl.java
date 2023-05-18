package core.basesyntax.service;

import core.basesyntax.InvalidDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH_LOGIN = 6;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new InvalidDataException("User is null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Invalid login. Null is not a valid value");
        }
        if (user.getLogin().length() < MIN_LENGTH_LOGIN) {
            throw new InvalidDataException("Login shouldn't be less "
                    + MIN_LENGTH_LOGIN + " characters");
        }
        User comparedLogin = storageDao.get(user.getLogin());
        if (comparedLogin != null) {
            throw new InvalidDataException("This login is taken");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new InvalidDataException("Invalid password. Null is not a valid value");
        }
        if (user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new InvalidDataException("Password shouldn't be less "
                    + MIN_LENGTH_PASSWORD + " characters");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new InvalidDataException("Invalid age. Null is not a valid value");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("Your age least than "
                    + MIN_AGE + " year old");
        }
    }
}
