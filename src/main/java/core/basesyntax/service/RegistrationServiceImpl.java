package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASS_LENGTH = 6;
    private static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidation(user);
        passwordValidation(user);
        ageValidation(user);
        loginValidation(user);
        storageDao.add(user);
        return user;
    }

    private void passwordValidation(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new InvalidUserException("Minimal password length - " + MIN_PASS_LENGTH);
        }
    }

    public void ageValidation(User user) {
        if (user.getAge() == null || user.getAge() < MIN_USER_AGE) {
            throw new InvalidUserException("Minimal user age - " + MIN_USER_AGE);
        }
    }

    private void loginValidation(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null
                || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserException("Wrong login - " + user.getLogin());
        }
    }

    private void userValidation(User user) {
        if (user == null) {
            throw new InvalidUserException("User can`t be null!");
        }
    }
}
