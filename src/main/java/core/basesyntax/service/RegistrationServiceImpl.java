package core.basesyntax.service;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null.");
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException("Login must be at least "
                    + MIN_LOGIN_LENGTH
                    + " characters long.");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User with login '"
                    + user.getLogin()
                    + "' already exists.");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Password must be at least "
                    + MIN_PASSWORD_LENGTH
                    + " characters long.");
        }

        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("User must be at least " + MIN_AGE + " years old.");
        }

        return storageDao.add(user);
    }
}
