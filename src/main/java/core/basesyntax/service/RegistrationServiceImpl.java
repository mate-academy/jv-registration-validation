package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USERS_AGE = 18;
    private static final int MAX_USERS_AGE = 150;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("Input data is null.");
        }
        if (user.getAge() == null
                || user.getAge() < MIN_USERS_AGE
                || user.getAge() > MAX_USERS_AGE) {
            throw new RegistrationException("User's age must be between "
                    + MIN_USERS_AGE + " and " + MAX_USERS_AGE + ".");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length must be at least "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login is already exists. ");
        }
        return storageDao.add(user);
    }
}
