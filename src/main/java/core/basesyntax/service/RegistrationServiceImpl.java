package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_CHARS = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_CHARS) {
            throw new RegistrationException("Login must be at least "
                    + MIN_CHARS + " characters long.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with such login exists.");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_CHARS) {
            throw new RegistrationException("Password must be at least "
                    + MIN_CHARS + " characters long");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age must be at least 18 years old");
        }
        storageDao.add(user);
        return user;
    }
}
