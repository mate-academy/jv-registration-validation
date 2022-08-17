package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_USER_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        user = checkUserNotNull(user);
        user = checkUserLogin(user);
        user = checkUserAge(user);
        user = checkUserPassword(user);
        if (user != null) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    private User checkUserNotNull(User user) {
        if (user == null) {
            throw new RuntimeException("user cannot be null");
        }
        return user;
    }

    private User checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("user login cannot be null");
        }
        if (storageDao.get(user.getLogin()) == null) {
            return user;
        }
        return null;
    }

    private User checkUserAge(User user) {
        if (user.getAge() >= MINIMUM_USER_AGE) {
            return user;
        }
        return null;
    }

    private User checkUserPassword(User user) {
        if (user.getPassword() == null) {
            return null;
        }
        if (user.getPassword().length() >= MINIMUM_PASSWORD_LENGTH) {
            return user;
        }
        return null;
    }
}
