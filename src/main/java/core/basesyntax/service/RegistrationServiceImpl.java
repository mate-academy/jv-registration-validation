package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("Login is null");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must contain at least 6 characters");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Such a login exists");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password is null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must contain at least 6 characters");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age is null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be at least 18 years old");
        }
        return storageDao.add(user);
    }
}
