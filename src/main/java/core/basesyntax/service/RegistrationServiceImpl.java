package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateNull(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateNull(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age cant't be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException("Not valid login: " + user.getLogin()
                    + ". Min allowed login length is " + MIN_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException("Not valid password: " + user.getPassword()
                    + ". Min allowed password length is " + MIN_LENGTH);
        }
    }

    private void validateAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User should be at least 18 years old");
        }
    }
}
