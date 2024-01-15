package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao = new StorageDaoImpl();

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        User existingUser = storageDao.get(user.getLogin());
        if (existingUser != null) {
            throw new InvalidUserDataException("User with this login already exists");
        }
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException("Login should be at least "
                    + MIN_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Password should be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("User should be at least "
                    + MIN_AGE + " years old");
        }
    }
}

