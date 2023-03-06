package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
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
    }

    private void validatePassword(String password) throws RegistrationException {
        if (password == null) {
            throw new RegistrationException("Password cannot be null.");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException(
                    "Password is too short, should be at least "
                            + MIN_PASSWORD_LENGTH
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
        if (age < 18) {
            throw new RegistrationException("Registration is forbidden for users below 18 y.o.");
        }
    }
}
