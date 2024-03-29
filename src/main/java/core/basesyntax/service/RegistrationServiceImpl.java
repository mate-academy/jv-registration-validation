package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidUserDataException {
        if (user == null || user.getLogin() == null || user.getPassword() == null) {
            throw new InvalidUserDataException("User, login, and password cannot be null");
        }

        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new InvalidUserDataException("A user with this login already exists");
        }

        if (user.getLogin().length() < 6 || user.getPassword().length() < 6) {
            throw new InvalidUserDataException("Login and password must be at least 6 characters");
        }

        if (user.getAge() < 18) {
            throw new InvalidUserDataException("The user must be at least 18 years old");
        }

        return storageDao.add(user);
    }
}
