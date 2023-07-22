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
        if (user == null) {
            throw new RegistrationException("There is no user to add");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with same login already exists");
        }
        if (user.getLogin() == null || user.getLogin().length() < FIELDS_MIN_LENGTH) {
            throw new RegistrationException("Login should be at least " + FIELDS_MIN_LENGTH
                    + " characters long. " + "User's login is "
                    + (user.getLogin() == null ? null : user.getLogin().length()));
        }
        if (user.getPassword() == null || user.getPassword().length() < FIELDS_MIN_LENGTH) {
            throw new RegistrationException("Password should be at least "
                    + FIELDS_MIN_LENGTH + " characters long. " + "User's password is "
                    + (user.getPassword() == null ? null : user.getLogin().length()));
        }
        if (user.getAge() == null || user.getAge() < USER_MIN_AGE) {
            throw new RegistrationException("Age should be at least "
                    + USER_MIN_AGE + ", user's age is "
                    + (user.getAge() == null ? null : user.getLogin().length()));
        }
        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
