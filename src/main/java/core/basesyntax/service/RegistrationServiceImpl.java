package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final int minPasswordLength = 6;
    private final int minLoginLength = 6;
    private final int minAge = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new InvalidUserDataException("User with this login already exists");
        }

        if (user.getLogin() == null || user.getLogin().length() < minLoginLength) {
            throw new InvalidUserDataException("Login cannot be null, "
                    + "empty or less than 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < minPasswordLength) {
            throw new InvalidUserDataException("Password cannot be null, "
                    + "empty or less than 6 characters");
        }

        if (user.getAge() == null || user.getAge() < minAge) {
            throw new InvalidUserDataException("User must be at "
                    + "least 18 years old");
        }

        storageDao.add(user);
        return user;
    }
}
