package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LENGTH_OF_LOGIN_AND_PASSWORD = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
        validateLogin(user);
        validatePassword(user);
        validateAge(user);

        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null");
        }

        User userWithSameLogin = storageDao.get(user.getLogin());

        if (userWithSameLogin != null && user.getLogin()
                .equals(userWithSameLogin.getLogin())) {
            throw new RegistrationException("Users with same login already exist");
        }

        if (user.getLogin().length() < LENGTH_OF_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("User's login must be at least 6 characters");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null");
        }

        if (user.getPassword().length() < LENGTH_OF_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("User's password must be at least 6 characters");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User's age must be at least 18 years");
        }
    }
}
