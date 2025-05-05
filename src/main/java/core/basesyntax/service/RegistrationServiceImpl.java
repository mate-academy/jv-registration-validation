package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_PASS_LENGTH = 6;
    private static final int ADULT_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new NullPointerException("Login can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("It's already exists user by login " + user.getLogin());
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new NullPointerException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASS_LENGTH) {
            throw new RuntimeException("Password must contains at least "
                    + MIN_PASS_LENGTH + " characters");
        }
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new NullPointerException("Age can't be null");
        }
        if (user.getAge() <= ADULT_AGE) {
            throw new RuntimeException("Can't register user with age " + user.getAge());
        }
    }
}
