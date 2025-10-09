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
        if (user == null) {
            throw new RegistrationServiceException("User should not be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login must not be null!");
        }
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Password must not be null!");
        }
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Age must not be null!");
        }
        if (user.getLogin().length() < 6 || user.getPassword().length() < 6) {
            throw new RegistrationServiceException(
                    "Register failed. Please check your username and password."
            );
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException(
                    "Your login " + user.getLogin() + " already exists."
            );
        }
        if (user.getAge() < 18) {
            throw new RegistrationServiceException(
                    "Your age is lower than 18."
            );
        }

        return storageDao.add(user);
    }
}
