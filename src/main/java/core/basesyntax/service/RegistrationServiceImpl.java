package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_CHAR_COUNT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) throws RegistrationException {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("Login, password, or age is missing");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login is already taken.");
        }

        if (user.getLogin().length() < MIN_CHAR_COUNT) {
            throw new RegistrationException("Login must be at least "
                    + MIN_CHAR_COUNT + " characters long.");
        }

        if (user.getPassword().length() < MIN_CHAR_COUNT) {
            throw new RegistrationException("Password must be at least "
                    + MIN_CHAR_COUNT + " characters long.");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be at least "
                    + MIN_AGE + " years old.");
        }
    }
}
