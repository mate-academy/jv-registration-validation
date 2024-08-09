package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_OR_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE_TO_REGISTER = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private boolean validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with the same login '"
                    + user.getLogin() + "' exists");
        }
        if (user.getLogin().length() < MIN_LOGIN_OR_PASSWORD_LENGTH) {
            throw new RegistrationException("User has invalid login: " + user.getLogin());
        }
        if (user.getPassword().length() < MIN_LOGIN_OR_PASSWORD_LENGTH) {
            throw new RegistrationException("User has invalid password: " + user.getPassword());
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < 0) {
            throw new RegistrationException("Invalid age value");
        }
        if (user.getAge() < MIN_AGE_TO_REGISTER) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE_TO_REGISTER);
        }
        return true;
    }
}
