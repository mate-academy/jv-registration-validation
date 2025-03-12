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
            throw new InvalidUserExeption("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserExeption("User with this login already exists");
        }
        if (user.getAge() >= MIN_AGE && user.getPassword().length() >= MIN_SYMBOLS
                && user.getLogin().length() >= MIN_SYMBOLS) {
            return storageDao.add(user);
        }
        if (user.getLogin().length() < MIN_SYMBOLS || user.getPassword().length() < MIN_SYMBOLS
                || user.getAge() < MIN_AGE) {
            throw new InvalidUserExeption("Incorrect data");
        }
        return null;
    }
}
