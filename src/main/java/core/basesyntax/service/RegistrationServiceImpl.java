package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_USER_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can`t be null.");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User login can`t be null.");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User password can`t be null.");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User age can`t be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Such login is already exist.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RegistrationException("User must be at least"
                   + " 18 years old, but was " + user.getAge() + ".");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("User password must have at least "
                    + "6 characters, but was " + user.getPassword().length() + ".");
        }
        return storageDao.add(user);
    }
}
