package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHAR = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login is already in the storage");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_CHAR) {
            throw new RegistrationException("Login can't be empty or less than " + MIN_CHAR);
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_CHAR) {
            throw new RegistrationException("Password can't be null or less than " + MIN_CHAR);
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException(user.getAge()
                    + " isn't valid. Minimum age allowed age is " + MIN_AGE);
        }
        return storageDao.add(user);
    }
}
