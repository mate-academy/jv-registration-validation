package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_PASSWORD_LENGTH;
    private static final int MINIMAL_USER_AGE;
    private final StorageDao storageDao = new StorageDaoImpl();

    static {
        MINIMAL_PASSWORD_LENGTH = 6;
        MINIMAL_USER_AGE = 18;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        validateLogin(user.getLogin());
        validatePassword(user.getPassword());
        validateAge(user.getAge());
        checkForDuplicate(user);
    }

    private void checkForDuplicate(User user) throws RegistrationException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User " + user.getLogin() + " already exists!");
        }
    }

    private void validateLogin(String login) throws RegistrationException {
        if (login == null) {
            throw new RegistrationException("Login cannot be null.");
        }
        if (login.isEmpty()) {
            throw new RegistrationException("Login cannot be empty.");
        }
        if (login.trim().length() != login.length()) {
            throw new RegistrationException("Login cannot contain blank symbols "
                    + "at the start or at the end of string.");
        }
    }

    private void validatePassword(String password) throws RegistrationException {
        if (password == null) {
            throw new RegistrationException("Password cannot be null.");
        }
        if (password.length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Password is too short, should be at least "
                            + MINIMAL_PASSWORD_LENGTH
                            + " characters long.");
        }
    }

    private void validateAge(Integer age) throws RegistrationException {
        if (age == null) {
            throw new RegistrationException("Age cannot be null.");
        }
        if (age <= 0) {
            throw new RegistrationException("Age should be a positive number.");
        }
        if (age < MINIMAL_USER_AGE) {
            throw new RegistrationException("Registration is forbidden for users below 18 y.o.");
        }
    }
}
