package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_REGISTRATION_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws RegistrationException {
        if (user == null) {
            throw new RegistrationException("the user was entered incorrectly, please enter again");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("this user is already registered");
        }
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationException("the login length must be at least"
                    + MIN_LOGIN_LENGTH + "characters");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationException("the login length must be at least"
                    + MIN_PASSWORD_LENGTH + "characters");
        }
        if (user.getAge() < MIN_REGISTRATION_AGE) {
            throw new RegistrationException("The user must be at least"
                    + MIN_REGISTRATION_AGE + "years old");
        }
        return storageDao.add(user);
    }
}
