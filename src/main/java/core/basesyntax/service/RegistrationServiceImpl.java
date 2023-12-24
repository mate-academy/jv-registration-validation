package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login already exist");
        }
        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("Login must be at least 6 characters long");
        }
        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("Password must be at least 6 characters long");
        }
        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidDataException("You must be at least 18 years old to register account");
        }
        storageDao.add(user);
        return user;
    }
}
