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
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with login '" + user.getLogin() + "' already exists.");
        }

        if (user.getLogin() == null) {
            throw new InvalidUserException("Login is required");
        }

        if (user.getLogin().length() < 6) {
            throw new InvalidUserException("Invalid login '" + user.getLogin()
                    +"'. Login must be at least '"
                    + MIN_LOGIN_LENGTH + "' characters long.");
        }

        if (user.getPassword() == null) {
            throw new InvalidUserException("Password is required");
        }

        if (user.getPassword().length() < 6) {
            throw new InvalidUserException("Invalid password '" + user.getPassword()
                    + "'. Password must be at least '"
                    + MIN_PASSWORD_LENGTH + "' characters long.");
        }

        if (user.getAge() == null) {
            throw new InvalidUserException("Age is required");
        }

        if (user.getAge() < 18 || user.getAge() > 2147483647) {
            throw new InvalidUserException("Invalid age '" + user.getAge()
                    + "'. User must be at least '" + MIN_AGE + "' years old.");
        }
    }
}
