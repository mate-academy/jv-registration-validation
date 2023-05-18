package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        validateUser(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with such login already exists");
        }
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException("Invalid login");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("Invalid password");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserException("Invalid age");
        }
    }
}
