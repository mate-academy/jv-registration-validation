package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LENGTH_OF_LOGIN_AND_PASSWORD = 6;
    private static final int USERS_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }

        if (user.getLogin() == null) {
            throw new RegistrationException("Login cannot be null or empty");
        } else if (user.getPassword() == null) {
            throw new RegistrationException("Password cannot be null or empty");
        } else if (user.getAge() == null) {
            throw new RegistrationException("Age cannot be null or empty");
        }

        User userWithSameLogin = storageDao.get(user.getLogin());

        if (userWithSameLogin != null && user.getLogin()
                .equals(userWithSameLogin.getLogin())) {
            throw new RegistrationException("Users with same login already exist");
        }

        if (user.getLogin().length() < LENGTH_OF_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("User's login must be at least 6 characters");
        } else if (user.getPassword().length() < LENGTH_OF_LOGIN_AND_PASSWORD) {
            throw new RegistrationException("User's password must be at least 6 characters");
        }

        if (user.getAge() < USERS_AGE) {
            throw new RegistrationException("User's age must be at least 18 years");
        }
        return storageDao.add(user);
    }
}
