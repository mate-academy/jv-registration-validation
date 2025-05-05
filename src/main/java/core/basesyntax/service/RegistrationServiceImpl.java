package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }

        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login cannot be less than " + MIN_LOGIN_LENGTH
                    + ". Actual login " + user.getLogin() + " length: " + user.getLogin().length());
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password cannot be less than " + MIN_PASSWORD_LENGTH
                    + ". Actual password " + user.getPassword()
                    + " length: " + user.getPassword().length());
        }

        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age cannot be less than " + MIN_AGE
                    + ". Actual age " + user.getAge());
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        storageDao.add(user);
        return user;
    }
}
