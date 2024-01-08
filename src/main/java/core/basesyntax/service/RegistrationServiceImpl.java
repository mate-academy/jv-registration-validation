package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserIsNull(user);
        checkUserLogin(user);
        checkUserPassword(user);
        checkUserAge(user);
        checkExistingUser(user);
        return storageDao.add(user);
    }

    private void checkUserIsNull(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be NULL !!!");
        }
    }

    private void checkUserLogin(User user) {
        if (user.getLogin() == null || user.getLogin().length() < MIN_LENGTH
                || user.getLogin().isBlank()) {
            throw new RegistrationException("User's login can't be null or less than "
                    + MIN_LENGTH + " characters");
        }
    }

    private void checkUserPassword(User user) {
        if (user.getPassword() == null || user.getPassword().length() < MIN_LENGTH
                || user.getPassword().isBlank()) {
            throw new RegistrationException("User's password can't be null or less than "
                    + MIN_LENGTH + " characters");
        }
    }

    private void checkUserAge(User user) {
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User age can't be NULL or less than "
                    + MIN_AGE + " years");
        }
    }

    private void checkExistingUser(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with current login already exist !!!");
        }
    }
}
