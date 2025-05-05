package core.basesyntax.service;

import core.basesyntax.InvalidUserException;
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
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException("Invalid login: " + user.getLogin()
                    + ". Login should be at least " + MIN_LOGIN_LENGTH + " characters long.");
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserException("Invalid password: " + user.getPassword()
                    + ". Password should be at least " + MIN_PASSWORD_LENGTH + " characters long.");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserException("Invalid age: " + user.getAge()
                    + ". Age should be at least " + MIN_AGE + " years.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with login already exists: " + user.getLogin());
        }
    }
}
