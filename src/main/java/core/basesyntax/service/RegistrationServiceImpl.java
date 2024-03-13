package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMAL_LOGIN_LENGTH = 6;
    public static final int MINIMAL_PASSWORD_LENGTH = 6;
    public static final int MINIMAL_USER_AGE = 18;

    private StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("Login is too short! Minimal length is "
                    + MINIMAL_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is too short! Minimal length is "
                    + MINIMAL_PASSWORD_LENGTH);
        }
        if (user.getAge() < MINIMAL_USER_AGE) {
            throw new RegistrationException("User is too young! Minimal age is "
                    + MINIMAL_USER_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login not available!");
        }
        return storageDao.add(user);
    }
}
