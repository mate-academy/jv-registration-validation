package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Invalid user data");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("A user with this login already exists");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Login must have more than 6 characters");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Password must have more than 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("The user must be over 18 years old");
        }
        return storageDao.add(user);
    }
}
