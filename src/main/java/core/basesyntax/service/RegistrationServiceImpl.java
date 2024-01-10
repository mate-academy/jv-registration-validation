package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkUserForNull(user);
        checkForValidLength(user);
        checkIfUserAlreadyExist(user);
        checkForValidAge(user);
        return storageDao.add(user);
    }

    private void checkUserForNull(User user) {
        if (user == null) {
            throw new UserRegistrationException("Can't register user, because user is null");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("The age of the user can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("The password can't be null");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("The login can't be null");
        }
    }

    private void checkIfUserAlreadyExist(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User already exists with login "
                    + user.getLogin());
        }
    }

    private void checkForValidLength(User user) {
        if (user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new UserRegistrationException("The length of the login must be at least "
                    + LOGIN_MIN_LENGTH);
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new UserRegistrationException("The length of the password must be at least "
                    + PASSWORD_MIN_LENGTH);
        }
    }

    private void checkForValidAge(User user) {
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("The age of the user must be at least " + MIN_AGE);
        }
    }
}
