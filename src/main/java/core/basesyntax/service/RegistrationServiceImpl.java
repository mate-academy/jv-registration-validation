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

    private void checkUser(User user) {
        if (user == null) {
            throw new RuntimeException("Put some user's value.");
        }
        checkLogin(user.getLogin());
        checkPassword(user.getPassword());
        checkAge(user.getAge());
    }

    private void checkLogin(String login) {
        if (login == null || login.length() == 0) {
            throw new RuntimeException("You need to enter your login.");
        } else if (storageDao.get(login) != null) {
            throw new RuntimeException("Your login already exists.");
        }
    }

    private void checkPassword(String password) {
        if (password == null) {
            throw new RuntimeException("You need to enter your password.");
        } else if (password.length() < PASSWORD_MINIMUM_LENGTH) {
            throw new RuntimeException("Your password should be minimum 6 symbols.");
        }
    }

    private void checkAge(int age) {
        if (age == 0) {
            throw new RuntimeException("You need to enter your age.");
        } else if (age < MINIMUM_VALID_AGE) {
            throw new RuntimeException("Your age should be 18 or higher.");
        }
    }
}
