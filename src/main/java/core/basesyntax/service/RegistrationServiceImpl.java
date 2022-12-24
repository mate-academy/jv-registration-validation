package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_AMOUNT_PASSWORD_CHARACTERS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        verifyUser(user);
        verifyLogin(user.getLogin());
        verifyAge(user.getAge());
        verifyPassword(user.getPassword());
        return storageDao.add(user);
    }

    private void verifyUser(User user) {
        if (user == null) {
            throw new UserRegistrationException("The user can't be null!");
        }
    }

    private void verifyLogin(String login) {
        if (login == null) {
            throw new UserRegistrationException("The login can't be null!");
        }
        if (storageDao.get(login) != null) {
            throw new UserRegistrationException("User with this login is already registered!");
        }
    }

    private void verifyAge(Integer age) {
        if (age == null) {
            throw new UserRegistrationException("Null age is invalid!");
        }
        if (age < 0) {
            throw new UserRegistrationException("The age can't be negative!");
        }
        if (age < MIN_AGE) {
            throw new UserRegistrationException("The user must be at least "
                    + MIN_AGE + " years old!");
        }
    }

    private void verifyPassword(String password) {
        if (password == null) {
            throw new UserRegistrationException("Null password is invalid!");
        }
        if (password.length() < MIN_AMOUNT_PASSWORD_CHARACTERS) {
            throw new UserRegistrationException("The password must contains at least "
                    + MIN_AMOUNT_PASSWORD_CHARACTERS + " characters!");
        }
    }
}
