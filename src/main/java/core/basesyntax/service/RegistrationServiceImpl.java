package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_SYMBOLS = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with this login already exists");
        }
        if (user.getLogin().length() < MIN_SYMBOLS) {
            throw new InvalidUserException("Login cannot be less than" + MIN_SYMBOLS);
        }
        if (user.getPassword().length() < MIN_SYMBOLS) {
            throw new InvalidUserException("Password cannot be less than" + MIN_SYMBOLS);
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("Age cannot be less than" + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
