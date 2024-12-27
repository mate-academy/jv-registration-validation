package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    private void validateUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH) {
            throw new InvalidUserException("Login must be at least 6 characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH) {
            throw new InvalidUserException("Password must be at least 6 characters long");
        }
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new InvalidUserException("User must be at least 18 years old");
        }

    }

    @Override
    public User register(User user) {
        validateUser(user);

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with login "
                    + user.getLogin()
                    + " already exists");
        }

        return storageDao.add(user);
    }
}
