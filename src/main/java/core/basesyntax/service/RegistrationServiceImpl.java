package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MINIMUM_AGE = 18;
    public static final int MINIMUM_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RuntimeException("User can't be null!");
        }

        if (user.getAge() == null) {
            throw new RuntimeException("Age can't be null!");
        }
        if (user.getLogin() == null) {
            throw new RuntimeException("Login can't be null!");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password can't be null!");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RuntimeException("For registration, user has to be at least minimum "
                    + MINIMUM_AGE + " years old");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RuntimeException("Password has to be minimum at least minimum "
                    + MINIMUM_PASSWORD_LENGTH + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already exist!");
        }
        return storageDao.add(user);
    }
}

