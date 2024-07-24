package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }

        if (user.getLogin() == null || user.getLogin().length() < 6) {
            throw new RegistrationException("login must contain more than 6 characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RegistrationException("Password must be at least 6 characters long");
        }

        if (user.getAge() < 18) {
            throw new RegistrationException("User must be over 18 years of age if:"
                    + user.getAge());
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("a user with such login or password already exists");
        }
        return storageDao.add(user);
    }
}
