package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.AgeRestrictionException;
import core.basesyntax.exceptions.LoginTooShortException;
import core.basesyntax.exceptions.PasswordTooShortException;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int LOGIN_MIN_LENGTH = 6;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int AGE_MINIMAL_LIMIT = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't be null!");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAlreadyExistException("User with same login already exist!");
        }
        if (user.getLogin().length() < LOGIN_MIN_LENGTH) {
            throw new LoginTooShortException("Login should be more than six characters!");
        }
        if (user.getPassword().length() < PASSWORD_MIN_LENGTH) {
            throw new PasswordTooShortException("Password should be more than six characters!");
        }
        if (user.getAge() < AGE_MINIMAL_LIMIT) {
            throw new AgeRestrictionException(
                    "User should be 18 years old or more!"
            );
        }
        return storageDao.add(user);
    }
}
