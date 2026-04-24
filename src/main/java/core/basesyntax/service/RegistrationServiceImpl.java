package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {

    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new RegistrationException("Login must be at least "
                    + MINIMAL_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least "
                    + MINIMAL_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new RegistrationException("User`s age must be more than" + MINIMAL_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exist");
        }
        return storageDao.add(user);
    }
}

