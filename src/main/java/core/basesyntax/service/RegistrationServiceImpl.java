package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_SIZE = 6;
    private static final int MIN_PASSWORD_SIZE = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl() {
        storageDao = new StorageDaoImpl();
    }

    @Override
    public User register(User user) {
        validateUser(user);
        validateLogin(user);
        checkUserExistence(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationServiceException("User can't be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationServiceException("Login can't be null");
        }
        if (user.getLogin().trim().length() < MIN_LOGIN_SIZE) {
            throw new RegistrationServiceException("Login is too short");
        }
    }

    private void checkUserExistence(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationServiceException("User has already been registered");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationServiceException("Password can't be null");
        }
        if (user.getPassword().trim().length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationServiceException("Password is too short");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationServiceException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationServiceException("Age isn't enough");
        }
    }
}
