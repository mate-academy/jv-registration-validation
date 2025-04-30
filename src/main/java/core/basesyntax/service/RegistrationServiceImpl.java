package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_DATA = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidDataException("User must not be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidDataException("Login must not be null");
        }
        if (user.getPassword() == null) {
            throw new InvalidDataException("Password must not be null");
        }
        if (findUserByLogin(user) != null) {
            throw new InvalidDataException("User with this login has been already registered");
        }
        if (user.getLogin().length() < MIN_LENGTH_DATA) {
            throw new InvalidDataException("Login must contain at least "
                    + MIN_LENGTH_DATA + " characters");
        }
        if (user.getPassword().length() < MIN_LENGTH_DATA) {
            throw new InvalidDataException("Password must contain at least "
                    + MIN_LENGTH_DATA + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidDataException("User's age must be at least " + MIN_AGE + " years old");
        }
        return storageDao.add(user);
    }

    private User findUserByLogin(User user) {
        String login = user.getLogin();
        return storageDao.get(login);
    }
}
