package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidation(user);
        passwordValidation(user.getPassword());
        checkIsExistingUser(user.getLogin());
        return storageDao.add(user);
    }

    private void checkIsExistingUser(String login) {
        if (storageDao.get(login) != null) {
            throw new RuntimeException("User with the same login is already exist.");
        }
    }

    private void passwordValidation(String password) {
        if (password.length() < PASSWORD_MIN_LENGTH) {
            throw new RuntimeException("Password must be at least 6 character");
        }
    }

    private void userValidation(User user) {
        if (user == null) {
            throw new RuntimeException("User cannot be null");
        }
        if (user.getAge() == null || user.getPassword() == null
                || user.getLogin() == null) {
            throw new RuntimeException("User fields cannot be null");
        }
        if (user.getAge() < 18) {
            throw new RuntimeException("Cannot register user with age less than 18");
        }
    }
}
