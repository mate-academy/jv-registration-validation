package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("User login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login "
                    + user.getLogin() + " already exists");
        }
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new InvalidDataException("User age must be at least "
                    + MIN_USER_AGE + " years old");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH_PASSWORD) {
            throw new InvalidDataException("User password must be at least "
                    + MIN_LENGTH_PASSWORD + " characters");
        }
        return storageDao.add(user);
    }
}
