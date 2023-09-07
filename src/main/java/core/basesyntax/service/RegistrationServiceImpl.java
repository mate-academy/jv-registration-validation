package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final static int AGE_LOWER_LIMIT = 18;
    private final static int LOGIN_MIN_LENGTH = 6;
    private final static int PASSWORD_MIN_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null!");
        }

        if (user.getAge() == null || user.getAge() < AGE_LOWER_LIMIT) {
            throw new RegistrationException("User age not defined or under age limit!");
        }

        if (user.getLogin() == null || user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new RegistrationException("User login NULL or under login length limit of "
                    + LOGIN_MIN_LENGTH + "!");
        }

        if (user.getPassword() == null || user.getPassword().length() < LOGIN_MIN_LENGTH) {
            throw new RegistrationException("User password NULL or under password length limit of "
                    + PASSWORD_MIN_LENGTH + "!");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User '" + user.getLogin() + "' already exists!");
        }

        return storageDao.add(user);
    }
}
