package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_AGE = 18;
    public static final int MIN_USERNAME_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login is exist, change your login");
        }
        if (user.getLogin().length() < MIN_USERNAME_LENGTH) {
            throw new RegistrationException("Login is less than 6 characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is less than 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age is less than 18 year");
        }
        return storageDao.add(user);
    }
}
