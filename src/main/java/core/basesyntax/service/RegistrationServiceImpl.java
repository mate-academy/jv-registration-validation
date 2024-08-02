package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.UserValidationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int USER_MIN_AGE = 18;
    private static final int USER_MIN_INPUT = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserValidationException {
        if (user == null) {
            throw new UserValidationException("Null user or user parameter");
        }
        if (user.getLogin() == null) {
            throw new UserValidationException("Login can't be null");
        }
        if (user.getAge() == null) {
            throw new UserValidationException("Age can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserValidationException("Password can't be null");
        }
        if (storageDao.get(user.getLogin()).equals(user)) {
            throw new UserValidationException("User " + user + " already exist");
        }
        if (user.getLogin().length() < USER_MIN_INPUT
                || user.getPassword().length() < USER_MIN_INPUT) {
            throw new UserValidationException("User's login or password less than 6 characters");
        }
        if (user.getAge() < USER_MIN_AGE) {
            throw new UserValidationException("User is under 18 years old");
        }
        return storageDao.add(user);
    }
}
