package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login can't be null!");
        }
        if (user.getLogin().length() < MINIMAL_LENGTH) {
            throw new InvalidDataException("Login should contain at least 6 characters!");
        }
        if (user.getAge() == null) {
            throw new InvalidDataException("Age cant be null!");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new InvalidDataException("User must be at least 18 years old!");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password can't be null!");
        }
        if (user.getPassword().length() < MINIMAL_LENGTH) {
            throw new InvalidDataException("Password should contain at least 6 characters!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with this login already exists!");
        }
        storageDao.add(user);
        return user;
    }
}
