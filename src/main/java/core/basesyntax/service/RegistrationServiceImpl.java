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
            throw new InvalidDataException("User cannot be null.");
        }

        if (user.getAge() == null || user.getAge() < 18) {
            throw new InvalidDataException("User's age must be at least 18 years.");
        }

        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new InvalidDataException("User's login cannot be null or empty.");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new InvalidDataException("User's password cannot be null or empty.");
        }

        if (user.getLogin().length() < 6) {
            throw new InvalidDataException("User's login must be at least 6 characters long.");
        }

        if (user.getPassword().length() < 6) {
            throw new InvalidDataException("User's password must be at least 6 characters long.");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login is already registered.");
        }

        return storageDao.add(user);
    }
}
