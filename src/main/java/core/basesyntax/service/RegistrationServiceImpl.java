package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserNotNull(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        validateUserUniqueness(user);
        return storageDao.add(user);
    }

    public void validateUserNotNull(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new RegistrationException("Login length is " + user.getLogin().length()
                    + ", the login cannot contain less than 6 characters.");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length is " + user.getPassword().length()
                    + ", the password cannot contain less than 6 characters.");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("User age is " + user.getAge()
                    + ". Age cannot be less than 18.");
        }
    }

    public void validateUserUniqueness(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with login %s already exists."
                        .formatted(user.getLogin()));
        }
    }
}
