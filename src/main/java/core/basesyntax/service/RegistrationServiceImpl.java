package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_ACCEPTABLE_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists!");
        }

        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("Login must contain at least "
                                            + MIN_LOGIN_LENGTH + " characters.");
        }

        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("Password must contain at least "
                                            + MIN_PASSWORD_LENGTH + " characters.");

        }

        if (user.getAge() == null || user.getAge() < MIN_ACCEPTABLE_AGE) {
            throw new RegistrationException("Not valid age! Min allowed age is "
                                            + MIN_ACCEPTABLE_AGE + ".");
        }

        return storageDao.add(user);
    }
}
