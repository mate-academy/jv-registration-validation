package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final Integer MIN_AGE;
    private static final Integer MIN_LOGIN_LENGTH;
    private static final Integer MIN_PASSWORD_LENGTH;

    private final StorageDao storageDao = new StorageDaoImpl();

    static {
        MIN_AGE = 18;
        MIN_LOGIN_LENGTH = 6;
        MIN_PASSWORD_LENGTH = 6;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Error : the user cannot be null.");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login '"
                    + user.getLogin() + "' already exists");
        }
        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LOGIN_LENGTH + " characters.");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Not valid age: "
                    + user.getAge() + ". Min allowed age is " + MIN_AGE + ".");
        }
    }
}
