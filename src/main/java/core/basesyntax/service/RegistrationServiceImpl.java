package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserNotNull(user);
        checkUserNotRegisteredYet(user);
        checkUserAge(user);
        checkUserLoginLength(user);
        checkUserPasswordLength(user);
        return storageDao.add(user);
    }

    private void checkUserNotNull(User user) {
        if (user == null) {
            throw new InvalidInputDataException("User can't be null!");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null || user.getAge() < MINIMUM_AGE) {
            throw new InvalidInputDataException("User's age is less than "
                    + MINIMUM_AGE + " or not specified");
        }
    }

    private void checkUserLoginLength(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MINIMUM_LENGTH) {
            throw new InvalidInputDataException("Your login is shorter than "
                    + MINIMUM_LENGTH + "or not specified");
        }
    }

    private void checkUserPasswordLength(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MINIMUM_LENGTH) {
            throw new InvalidInputDataException("Your password is shorter than "
                            + MINIMUM_LENGTH + " or not specified");
        }
    }

    private void checkUserNotRegisteredYet(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputDataException(
                    "User with this login - " + user.getLogin()
                            + " - already registered. Try to log in");
        }
    }
}
