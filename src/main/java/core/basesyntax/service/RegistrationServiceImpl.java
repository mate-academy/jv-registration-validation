package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_SIZE = 6;
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_SIZE = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateUserData(user);
        if (storageDao.get(user.getLogin()) == null) {
            return storageDao.add(user);
        }
        return null;
    }

    private void validateUserData(User user) {
        if (user == null) {
            throw new RegistrationException(
                    "User do not exist, must be not null-value");
        }
        validateLogin(user);
        validateAge(user);
        validatePassword(user);
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_SIZE) {
            throw new RegistrationException(
                    "Invalid user password for registration, available: "
                            + "Notnull and contents at list ["
                            + MIN_LOGIN_SIZE + "] elements");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null
                || user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    "Invalid user age for registration, available: "
                            + "Notnull and at list ["
                            + MIN_LOGIN_SIZE + "] years");
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null
                || user.getLogin().length() < MIN_LOGIN_SIZE) {
            throw new RegistrationException(
                    "Invalid login for registration, available: "
                            + "Notnull with length at list ["
                            + MIN_LOGIN_SIZE + "] symbols");
        }
    }
}
