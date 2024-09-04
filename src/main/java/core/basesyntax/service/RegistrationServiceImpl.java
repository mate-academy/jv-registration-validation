package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        storageDao.add(user);
        return user;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("Failed to add new user! \n"
                    + "User object is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Failed to add new user! \n"
                    + "Login should not be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Failed to add new user! \n"
                    + "Password should not be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Failed to add new user! \n"
                    + "Age should not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Failed to add new user! \n"
                    + "User with login " + user.getLogin() + " already exists.");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Failed to add new user! \n"
                    + "Login should have at least 6 characters.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Failed to add new user! \n"
                    + "Password should have at least 6 characters.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("Failed to add new user! \n"
                    + "User should be at least 18 years old!");
        }
    }
}
