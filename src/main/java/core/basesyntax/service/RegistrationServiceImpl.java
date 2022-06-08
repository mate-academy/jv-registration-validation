package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MINIMUM_LENGTH = 6;
    private static final int MINIMUM_VALID_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUser(user);
        return storageDao.add(user);
    }

    public void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("Put some user's value.");
        }
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
    }

    public void checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("You need to enter your login.");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Your login already exists.");
        }
    }

    public void checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("You need to enter your password.");
        } else if (user.getPassword().length() < PASSWORD_MINIMUM_LENGTH) {
            throw new RuntimeException("Your password should be minimum 6 symbols.");
        }
    }

    public void checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("You need to enter your age.");
        } else if (user.getAge() < MINIMUM_VALID_AGE) {
            throw new RuntimeException("Your age should be 18 or higher.");
        }
    }
}
