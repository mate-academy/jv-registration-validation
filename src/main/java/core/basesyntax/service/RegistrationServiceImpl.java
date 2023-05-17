package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_AND_LOGIN_LENGTH = 6;
    private static final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        invalidUser(user);
        invalidData(user);
        invalidLogin(user.getLogin());
        return storageDao.add(user);
    }

    private static void invalidUser(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }
    }

    private static void invalidLogin(String login) {
        if (storageDao.get(login) != null) {
            throw new InvalidUserDataException("User with the same login already exists");
        }
    }

    private static void invalidData(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_PASSWORD_AND_LOGIN_LENGTH
                || user.getPassword() == null
                || user.getPassword().length() < MIN_PASSWORD_AND_LOGIN_LENGTH
                || user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Invalid user data");
        }
    }
}





