package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeptionforservice.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MINIMUM_PASSWORD_LENGTH = 6;
    private static final int MINIMUM_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        userVarification(user);
        return storageDao.add(user);
    }

    private void userVarification (User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("Login null input.Try again!");
        }

        if (user.getPassword() == null) {
            throw new RegistrationException("Password null input. Try again!");
        }

        if (user.getLogin().length() < MINIMUM_LOGIN_LENGTH) {
            throw new RegistrationException("Invalid login format "
                    + user.getLogin() + ". Min length login : " + MINIMUM_LOGIN_LENGTH);
        }

        if (user.getPassword().length() < MINIMUM_PASSWORD_LENGTH) {
            throw new RegistrationException("Invalid login format "
                    + user.getLogin() + ". Min length password: " + MINIMUM_PASSWORD_LENGTH);
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Invalid login format "
                    + user.getLogin() + ". Age was less than should be " + MIN_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
    }
}
