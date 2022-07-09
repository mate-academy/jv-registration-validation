package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE_ALLOWED = 18;
    private static final int MAX_AGE_ALLOWED = 125;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    public static int getMinAgeAllowed() {
        return MIN_AGE_ALLOWED;
    }

    public static int getMaxAgeAllowed() {
        return MAX_AGE_ALLOWED;
    }

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()) != null
                && user.getLogin().equals(storageDao.get(user.getLogin()).getLogin())) {
            throw new RuntimeException("Can't register user " + user
                    + " User with such login " + user.getLogin()
                    + " already exists");
        }
        if (user.getAge() < MIN_AGE_ALLOWED || user.getAge() > MAX_AGE_ALLOWED) {
            throw new RuntimeException("Can't register user " + user
                    + " Allowed user age is from: " + MIN_AGE_ALLOWED
                    + " to: " + MAX_AGE_ALLOWED);
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password length should be at least "
                    + MIN_PASSWORD_LENGTH + " symbols");
        }
        return storageDao.add(user);
    }
}
