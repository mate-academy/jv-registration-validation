package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_REQUIRED_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cant be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Too short login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Too short password");
        }
        if (user.getAge() < MIN_REQUIRED_AGE) {
            throw new RegistrationException("User must be at least 18 years old");
        }

        return storageDao.add(user);
    }
}
