package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final static int MIN_LEN_OF_LOGIN_AND_PASSWORD = 6;
    private final static int MIN_AGE_OF_USERS = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        // Removed throwing NullPointerExceptions, now Java throws them
        if (user.getLogin().length() < MIN_LEN_OF_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("Login must contain at least 6 symbols");
        }
        if (user.getPassword().length() < MIN_LEN_OF_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("Password must contain at least 6 symbols");
        }
        if (user.getAge() < MIN_AGE_OF_USERS) {
            throw new RegistrationException("User must be older than 17 years");
        }
        if (Storage.people.contains(user)) {
            throw new RegistrationException("User is already registered");
        }

        storageDao.add(user);
        return user;
    }
}
