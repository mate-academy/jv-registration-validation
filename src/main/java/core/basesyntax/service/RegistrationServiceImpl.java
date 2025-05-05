package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User must not be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("A user with this login is already registered");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidUserException("Login must contain at least 6 characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserException("Password must contain at least 6 characters");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidUserException("You are not have 18 years old");
        }
        return storageDao.add(user);
    }
}
