package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        final int MIN_LOGIN_LENGTH = 6;
        final int MIN_PASSWORD_LENGTH = 6;
        final int MIN_AGE = 18;

        if (user == null) {
            throw new RegistrationException("User is null");
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login is less than " + MIN_LOGIN_LENGTH + " characters");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password is less than " + MIN_PASSWORD_LENGTH + " characters");
        }

        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age is less than " + MIN_AGE + " years");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }

        return storageDao.add(user);
    }
}
