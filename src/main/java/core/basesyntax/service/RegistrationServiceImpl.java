package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (isValid(user) && isLoginValid(user)
                          && isPasswordValid(user)
                          && isAgeValid(user)
                          && isLoginInStorage(user)) {
            storageDao.add(user);
        }
        return null;
    }

    private boolean isValid(User user) {
        if (user == null) {
            throw new NullPointerException("You didn't insert any data!");
        }

        return true;
    }

    private boolean isLoginValid(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("You have not entered your login.");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RuntimeException("Minimal login length is " + MIN_LOGIN_LENGTH);
        }

        return true;
    }

    private boolean isPasswordValid(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("You have not entered your password.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Minimal password length is " + MIN_PASSWORD_LENGTH);
        }

        return true;
    }

    private boolean isAgeValid(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("You have not entered your age.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Minimal age length is " + MIN_AGE);
        }

        return true;
    }

    private boolean isLoginInStorage(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("Your login is already used.");
        }

        return true;
    }
}
