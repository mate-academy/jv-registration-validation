package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int FIELDS_MIN_LENGTH = 6;
    private static final int USER_MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNulls(user);
        checkFields(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login already exists");
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }

    private void checkForNulls(User user) {
        if (user == null) {
            throw new RegistrationException("There is no user to add");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
    }

    private void checkFields(User user) {
        if (user.getLogin().length() < FIELDS_MIN_LENGTH) {
            throw new RegistrationException("Login should be at least " + FIELDS_MIN_LENGTH
                    + " characters long. User's login is "
                    + user.getLogin().length());
        }
        if (user.getPassword().length() < FIELDS_MIN_LENGTH) {
            throw new RegistrationException("Password should be at least "
                    + FIELDS_MIN_LENGTH + " characters long. " + "User's password is "
                    + user.getPassword().length());
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new RegistrationException("Age should be at least "
                    + USER_MIN_AGE + ", user's age is "
                    + user.getAge());
        }
    }
}
