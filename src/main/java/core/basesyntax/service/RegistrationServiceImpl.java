package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) == null) {//
            validateLogin(user);
            validatePassword(user);
            validateAge(user);
            storageDao.add(user);
            return user;
        } else {
            throw new RegistrationException("A user with this login already exists");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }
        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new RegistrationException("Not valid login " + user.getLogin()
                    + ".Login cannot be less than " + MINIMUM_LENGTH + " characters");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }
        if (user.getPassword().length() < MINIMUM_LENGTH) {
            throw new RegistrationException("Not valid password " + user.getPassword()
                    + ".Password cannot be less than " + MINIMUM_LENGTH + " characters");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("Not valid age: " + user.getAge()
                    + ".Age cannot be less than " + MINIMUM_AGE);
        }
    }
}
