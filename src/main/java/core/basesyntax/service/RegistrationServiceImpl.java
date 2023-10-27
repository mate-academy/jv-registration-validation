package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user); // Validate user data before registration
        return storageDao.add(user);
    }

    private void validateUserData(User user) {
        if (user.getLogin().length() < 6
                || user.getPassword().length() < 6
                || user.getAge() < 18) {
            throw new InvalidUserException("Invalid user data. "
                    + "Please check login, password, and age.");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new InvalidUserException("User with this login already exists.");
        }
    }

}
