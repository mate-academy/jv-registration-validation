package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new RegistrationException("Login cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RegistrationException("Password cannot be null or empty");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login length cannot be less than " + MIN_LOGIN_LENGTH);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password length cannot be less than "
                    + MIN_PASSWORD_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User cannot be under " + MIN_AGE);
        }
        if (user.getAge() > Integer.MAX_VALUE) {
            throw new RegistrationException("Age is incorrect");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exist");
        }
        storageDao.add(user);
        return user;

    }
}
