package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 2;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new RuntimeException("The value of data isn't correct.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User with login \""
                    + user.getLogin() + "\" already exists.");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RuntimeException("Login must be more than "
                    + MIN_LOGIN_LENGTH + " characters.");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RuntimeException("Password must be more than "
                    + MIN_PASSWORD_LENGTH + " characters.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RuntimeException("Age must be more than " + MIN_AGE + " years.");
        }
        return storageDao.add(user);
    }
}
