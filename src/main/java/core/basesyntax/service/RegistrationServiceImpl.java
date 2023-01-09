package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userValidation(user);
        storageDao.add(user);
        return user;
    }

    private void userValidation(User user) {
        checkUser(user);
        checkAge(user);
        checkLogin(user);
        checkPassword(user);
    }

    private static void checkPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
    }

    private void checkLogin(User user) {
        if (user.getLogin() == null || storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("Invalid login");
        }
    }

    private static void checkAge(User user) {
        if (user.getAge() == null || user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("Age must be at least 18");
        }
    }

    private static void checkUser(User user) {
        if (user == null) {
            throw new RegistrationException("No user received");
        }
    }
}
