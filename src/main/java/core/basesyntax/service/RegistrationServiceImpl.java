package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_VALID_AGE = 18;
    private static final int DEFAULT_VALID_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidator(user);
        loginValidator(user);
        ageValidator(user);
        passwordValidator(user);
        return storageDao.add(user);
    }

    private void userValidator(User user) {
        if (user == null) {
            throw new NullPointerException("Can't register null user!");
        }
    }

    private void passwordValidator(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("User's password cannot be empty");
        }
        if (user.getPassword().length() < DEFAULT_VALID_PASSWORD) {
            throw new RuntimeException("User's password cannot be less then 6 symbols");
        }
    }

    private void ageValidator(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("User's age cannot be empty");
        }
        if (user.getAge() < DEFAULT_VALID_AGE) {
            throw new RuntimeException("User's age cannot be less then 18");
        }
    }

    private void loginValidator(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("User's login cannot be empty");
        }
        if (user.equals(storageDao.get(user.getLogin()))) {
            throw new RuntimeException("User with this login already exists!");
        }
    }
}
