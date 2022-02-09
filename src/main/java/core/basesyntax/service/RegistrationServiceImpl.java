package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MINIMUM_PASSWORD_LENGTH = 6;
    private static final int USER_MINIMUM_AGE = 18;
    private final StorageDao storageDao;

    public RegistrationServiceImpl(StorageDao storageDao) {
        this.storageDao = storageDao;
    }

    @Override
    public User register(User user) {
        if (!validateUser(user)) {
            return null;
        }
        return storageDao.add(user);
    }

    private boolean validateUser(User user) {
        userForNull(user);
        loginValidate(user);
        passwordValidate(user);
        ageValidate(user);
        validateUserExists(user);
        return true;
    }

    private void userForNull(User user) {
        if (user == null) {
            throw new NullPointerException("User is null, you can't register user with null");
        }
    }

    private void loginValidate(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User login is null");
        }
    }

    private void ageValidate(User user) {
        if (user.getAge() == null || user.getAge() < USER_MINIMUM_AGE) {
            throw new RuntimeException("Can't register user, with age less than 18");
        }
    }

    private void passwordValidate(User user) {
        if (user.getPassword() == null
                || user.getPassword().length() < USER_MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("you password length don't correct: your length "
                    + user.getPassword().length() + "must be a " + USER_MINIMUM_PASSWORD_LENGTH);
        }
    }

    private boolean validateUserExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exists");
        }
        return true;
    }
}
