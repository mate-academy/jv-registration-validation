package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE_OF_USERS = 18;
    private static final int MIN_LEN_OF_LOGIN = 6;
    private static final int MIN_LEN_OF_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin().length() < MIN_LEN_OF_LOGIN) {
            throw new RegistrationException("Login must contain at least 6 symbols");
        }
        if (user.getPassword().length() < MIN_LEN_OF_PASSWORD) {
            throw new RegistrationException("Password must contain at least 6 symbols");
        }
        if (user.getAge() < MIN_AGE_OF_USERS) {
            throw new RegistrationException("User must be older than 17 years");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login was registered");
        }

        return storageDao.add(user);
    }
}
