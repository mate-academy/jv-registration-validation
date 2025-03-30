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
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age should be more than 18");
        }
        if (user.getLogin().length() < MIN_CHAR) {
            throw new RegistrationException("Login should be longer than 6 characters");
        }
        if (user.getPassword().length() < MIN_CHAR) {
            throw new RegistrationException("Password should be longer than 6 characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age should be more than 18");
        }
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        } else {
            throw new RegistrationException("User with this login already exists");
        }
    }
}
