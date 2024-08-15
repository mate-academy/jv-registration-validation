package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationFailed;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int CORRECT_LOGIN_PASSWORD_LENGHT = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationFailed("Login can't be " + null);
        }

        if (user.getPassword() == null) {
            throw new RegistrationFailed("Password can't be " + null);
        }

        if (user.getAge() == null) {
            throw new RegistrationFailed("Age can't be " + null);
        }

        if (user.getLogin().length() < CORRECT_LOGIN_PASSWORD_LENGHT) {
            throw new RegistrationFailed("Login length must be at least "
                    + CORRECT_LOGIN_PASSWORD_LENGHT
                    + " characters");
        }

        if (user.getPassword().length() < CORRECT_LOGIN_PASSWORD_LENGHT) {
            throw new RegistrationFailed("Password length must be at least "
                    + CORRECT_LOGIN_PASSWORD_LENGHT
                    + " characters");
        }

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationFailed("You must be older than --> " + MIN_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationFailed("Login already exists --> "
                    + storageDao.get(user.getLogin()));
        }

        return storageDao.add(user);
    }
}
