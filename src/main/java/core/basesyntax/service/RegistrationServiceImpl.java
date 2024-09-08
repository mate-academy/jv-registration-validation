package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_LENGHT = 6;
    public static final int AGE_MUST_BE_MORE_THAN = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGHT) {
            throw new InvalidUserException("Login must be at least 6 characters long");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_LOGIN_LENGHT) {
            throw new InvalidUserException("Password must be at least 6 characters long");
        }
        if (user.getAge() == null || user.getAge() < AGE_MUST_BE_MORE_THAN) {
            throw new InvalidUserException("User must be at least 18 years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
