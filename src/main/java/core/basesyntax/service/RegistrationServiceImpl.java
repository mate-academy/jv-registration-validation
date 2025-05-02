package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least " + MIN_LOGIN_LENGTH
                    + " characters");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already  exists");
        }
        storageDao.add(user);
        return user;
    }
}
