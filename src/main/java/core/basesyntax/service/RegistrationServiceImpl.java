package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALUE = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validate(user);
        return storageDao.add(user);
    }

    private void validate(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_VALUE) {
            throw new RegistrationException("Login must be greater than " + MIN_VALUE);
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_VALUE) {
            throw new RegistrationException("Password must be greater than " + MIN_VALUE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("There is a user with the same login");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age must be greater than " + MIN_AGE);
        }
    }
}
