package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserDataException("User shouldn't be null!");
        }
        checkUserAge(user);
        checkUserLoginLength(user);
        checkUserPasswordLength(user);
        checkUserNotRegisteredYet(user);
        return storageDao.add(user);
    }

    private void checkUserNotRegisteredYet(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserDataException("This user is already registered!");
        }
    }

    private void checkUserLoginLength(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException("Make your login length greater than"
                + (MIN_LOGIN_LENGTH - 1));
        }
    }

    private void checkUserPasswordLength(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException("Make your login length greater than"
                + (MIN_PASSWORD_LENGTH - 1));
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("Your age should be at least" + MIN_AGE);
        }
    }
}
