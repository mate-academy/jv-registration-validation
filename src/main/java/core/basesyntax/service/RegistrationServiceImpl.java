package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
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
            throw new RegistrationFailedException("Failed to add new user! \n"
                    + "User object is null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationFailedException("Failed to add new user! \n"
                    + "Login should not be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationFailedException("Failed to add new user! \n"
                    + "Password should not be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationFailedException("Failed to add new user! \n"
                    + "Age should not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailedException("Failed to add new user! \n"
                    + "User with login " + user.getLogin() + " already exists.");
        }
        if (user.getLogin().length() < 6) {
            throw new RegistrationFailedException("Failed to add new user! \n"
                    + "Login should have at least 6 characters.");
        }
        if (user.getPassword().length() < 6) {
            throw new RegistrationFailedException("Failed to add new user! \n"
                    + "Password should have at least 6 characters.");
        }
        if (user.getAge() < 18) {
            throw new RegistrationFailedException("Failed to add new user! \n"
                    + "User should be at least 18 years old!");
        }
    }
}
