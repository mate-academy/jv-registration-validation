package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login "
                    + user.getLogin() + " already exists.");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MIN_LENGTH + " characters long.");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_LENGTH + " characters long.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be at least "
                    + MIN_AGE + " years old.");
        }
        storageDao.add(user);
        return user;
    }
}
