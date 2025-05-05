package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int AGE_RESTRICTION = 18;
    private static final int PASSWORD_LENGTH_RESTRICTION = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        ageChecking(user);
        passwordChecking(user);
        loginChecking(user);
        return storageDao.add(user);
    }

    private void loginChecking(User user) {
        if (user.getLogin() == null) {
            throw new RuntimeException("For registration User must be set login");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RuntimeException("User already registered");
        }
    }

    private void passwordChecking(User user) {
        if (user.getPassword() == null) {
            throw new RuntimeException("For registration User must be set password");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH_RESTRICTION) {
            throw new RuntimeException("Password must be at least "
                    + PASSWORD_LENGTH_RESTRICTION + " characters");
        }
    }

    private void ageChecking(User user) {
        if (user.getAge() == null) {
            throw new RuntimeException("For registration User must be set age");
        }
        if (user.getAge() < AGE_RESTRICTION) {
            throw new RuntimeException("User must be at least " + AGE_RESTRICTION + " years old");
        }
    }
}
