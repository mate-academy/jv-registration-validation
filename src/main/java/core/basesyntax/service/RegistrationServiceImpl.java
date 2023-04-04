package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null.");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new RegistrationException("User age should be "
                    + USER_MIN_AGE + " or more!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this username is already exist");
        }
        if (user.getPassword() == null || user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new RegistrationException("Password is too short. Minimum length is "
                    + PASSWORD_MIN_LENGTH);
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        return storageDao.add(user);
    }
}
