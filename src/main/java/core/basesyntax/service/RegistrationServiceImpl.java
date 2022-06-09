package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final int FINAL_STATIC_ZERO = 0;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (checkAge(user) && checkLogin(user) && checkPassword(user)) {
            storageDao.add(user);
            return user;
        }
        return null;
    }

    private boolean checkAge(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        } else if (user.getAge() < MINIMAL_AGE) {
            throw new RuntimeException("Age must be 18 or greater for this action.");
        } else if (user.getAge() < FINAL_STATIC_ZERO) {
            throw new RuntimeException("Age can't be negative");
        }
        return true;
    }

    private boolean checkLogin(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        } else if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with same login is already exists");
        }
        return true;
    }

    private boolean checkPassword(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        } else if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be greater than 6 characters");
        }
        return true;
    }
}
