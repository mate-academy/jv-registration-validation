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
        validateUser(user);
        validateLogin(user);
        checkUserExistence(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can not be null");
        }
        if (user.getLogin().trim().length() < MIN_LOGIN_SIZE) {
            throw new RegistrationException("The login must contain at least 6 characters");
        }
    }

    private void checkUserExistence(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User is already registered");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can not be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationException("The password must contain at least 6 characters");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age can not be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be 18 years or older");
        }
    }
}
