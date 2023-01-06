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
        checkNullUser(user);
        checkUserLogin(user);
        checkUserAge(user);
        checkUserPassword(user);
        storageDao.add(user);
        return user;
    }

    private void checkNullUser(User user) {
        if (user == null) {
            throw new RuntimeException("user cannot be null");
        }
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("user login cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("a user with this login already exists " + user.getLogin());
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("user age cannot be null");
        }
        if (user.getAge() < MINIMUM_USER_AGE) {
            throw new RuntimeException("the age of the user cannot be less than"
                    + MINIMUM_USER_AGE + " years, you entered the age: " + user.getAge());
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("user password cannot be null");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("user password cannot be less than six characters");
        }
    }
}
