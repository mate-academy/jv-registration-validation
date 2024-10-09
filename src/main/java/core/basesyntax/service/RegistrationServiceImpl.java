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
        if (user.getLogin() == null) {
            throw new InvalidUserDataException(
                    "Login cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException(
                    "User with this login already exists");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidUserDataException(
                    "Login must be at least 6 characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserDataException(
                    "Password must be at least 6 characters long");
        }
        if (user.getAge() < 18) {
            throw new InvalidUserDataException(
                    "User must be at least 18 years old");
        }

        storageDao.add(user);

        return user;
    }
}
