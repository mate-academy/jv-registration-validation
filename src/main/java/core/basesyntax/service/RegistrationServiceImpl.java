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
        validateUserData(user);
        storageDao.add(user);
        return user;
    }

    private void validateUserData(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be NULL !!!");
        }
        if (user.getLogin() == null || user.getLogin().trim().length() < MIN_LENGTH) {
            throw new RegistrationException("User's login can't be null or less than "
                    + MIN_LENGTH + " characters");
        }
        if (user.getPassword() == null || user.getPassword().trim().length() < MIN_LENGTH) {
            throw new RegistrationException("User's password can't be null or less than "
                    + MIN_LENGTH + " characters");
        }
        if (user.getAge() == null || user.getAge() < MIN_AGE) {
            throw new RegistrationException("User age can't be NULL or less than "
                    + MIN_AGE + " years");
        }
        if (storageDao.get(user.getLogin()).equals(user)) {
            throw new RegistrationException("User with current login already exist !!!");
        }
    }
}
