package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_USER_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null.");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("User login can't be null.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login: " + user.getLogin()
                    + " already exists.");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password can't be less than " + MIN_PASSWORD_LENGTH
                    + " symbols.");
        }
        if (user.getAge() == null) {
            throw new RuntimeException("User age can't be null.");
        }
        if (user.getAge() < MIN_USER_AGE) {
            throw new RuntimeException("User age can't be less than " + MIN_USER_AGE
                    + " or be negative.");
        }
        return storageDao.add(user);
    }
}
