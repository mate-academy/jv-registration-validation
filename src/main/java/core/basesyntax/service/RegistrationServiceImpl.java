package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CREDS_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User already exists.");
        }
        if (user.getLogin().length() < MIN_CREDS_LENGTH) {
            throw new InvalidDataException("Login must be at least 6 characters long.");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_CREDS_LENGTH) {
            throw new InvalidDataException("Password must be at least 6 characters long.");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("You must be at least 18 years old to register.");
        }
        return storageDao.add(user);
    }
}
