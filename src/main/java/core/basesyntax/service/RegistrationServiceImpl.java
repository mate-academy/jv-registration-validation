package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.exception.InvalidUserException;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new InvalidUserException("Login must be at least 6 characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new InvalidUserException("Password must be at least 6 characters long");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidUserException("User must be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
