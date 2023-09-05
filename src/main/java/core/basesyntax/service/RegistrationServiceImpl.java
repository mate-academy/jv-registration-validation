package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_VALID_LENGTH = 6;
    private static final int MINIMAL_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        alreadyRegisteredValidation(user);
        loginAndPasswordsValidation(user);
        ageValidation(user);
        return storageDao.add(user);
    }

    private void alreadyRegisteredValidation(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with current login is already registered");
        }
    }

    private void loginAndPasswordsValidation(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_VALID_LENGTH) {
            throw new InvalidDataException("Incorrect login. Length should be at least "
                    + MIN_VALID_LENGTH);
        }
        if (user.getPassword() == null || user.getPassword().length() < MIN_VALID_LENGTH) {
            throw new InvalidDataException("Incorrect password. Length should be at least "
                    + MIN_VALID_LENGTH);
        }
    }

    private void ageValidation(User user) {
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new InvalidDataException("Your age should been at least " + MINIMAL_AGE);
        }
    }
}
