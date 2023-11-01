package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null!");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("You have to be over " + MINIMUM_AGE + "!");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null!");
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new RegistrationException("Login is too short! Minimum length: " + MINIMUM_LOGIN_LENGTH);
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null!");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is too short! Minimum length: " + MINIMUM_PASSWORD_LENGTH);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Login: " + user.getLogin() + " is already taken! Choose another one");
        }

        return storageDao.add(user);
    }
}
