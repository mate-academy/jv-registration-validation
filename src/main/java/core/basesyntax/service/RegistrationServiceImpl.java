package core.basesyntax.service;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_LENGTH = 6;
    private static final int PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNotNull(user);
        checkUserAlreadyExists(user);
        validateUserData(user);

        return storageDao.add(user);
    }

    private void checkUserNotNull(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }
    }

    private void checkUserAlreadyExists(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("User already exists");
        }
    }

    private void validateUserData(User user) {
        if (user.getLogin().length() < LOGIN_LENGTH) {
            throw new InvalidUserDataException("Login is too short. Minimum length required is "
                    + LOGIN_LENGTH + " characters");
        }
        if (user.getPassword().length() < PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Password is too short. Minimum length required is "
                    + PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Invalid age. Minimum age required is " + MIN_AGE);
        }
    }
}
