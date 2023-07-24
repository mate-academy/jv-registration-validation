package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserException("Login can't be null.");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException("Password can't be null.");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new InvalidUserException("User should be older than " + MINIMUM_AGE +
                    ". But was " + user.getAge());
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new InvalidUserException("User should get password length more than " +
                    MINIMUM_PASSWORD_LENGTH + ". But was " + user.getPassword().length());
        }
        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new InvalidUserException("User should get login length more than " +
                    MINIMUM_LOGIN_LENGTH + ". But was " + user.getLogin().length());
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("Such a user is already registered!");
        }

        return storageDao.add(user);
    }
}
