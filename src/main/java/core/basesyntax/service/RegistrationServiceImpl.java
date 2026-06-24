package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("User login can't be null");
        }

        if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("User login length must be at least 6 characters");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("User password can't be null");
        }

        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("User password length must be at least 6 characters");
        }

        if (user.getAge() == null) {
            throw new RegistrationException("User age can't be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User age must be minimum 18 years old");
        }

        return storageDao.add(user);
    }
}
