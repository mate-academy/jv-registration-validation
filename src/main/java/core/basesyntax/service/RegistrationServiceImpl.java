package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MAX_LOGIN_LENGTH = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }
        if (user.getAge() == null || user.getAge() < MAX_LOGIN_LENGTH) {
            throw new RegistrationException("User must be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with the same login already exists");
        }
        return storageDao.add(user);
    }
}
