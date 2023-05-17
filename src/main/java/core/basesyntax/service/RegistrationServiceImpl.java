package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MIN_LOGIN_SIZE = 6;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User is already registered");
        }
        return storageDao.add(validateLogin(validatePassword(validateAge(user))));
    }

    private User validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getLogin().trim().length() < MIN_LOGIN_SIZE) {
            throw new RegistrationException("Login is too short");
        }
        return user;
    }

    private User validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getPassword().trim().length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationException("Password is too short");
        }
        return user;
    }

    private User validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Age isn't enough");
        }
        return user;
    }
}
