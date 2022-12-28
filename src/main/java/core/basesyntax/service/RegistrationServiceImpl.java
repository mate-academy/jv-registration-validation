package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserRegistrationException {
        if (user.getLogin() == null) {
            throw new UserRegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Password less than "
                    + MIN_PASSWORD_LENGTH + " characters");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new UserRegistrationException("Age is less than " + MIN_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User" + user.getLogin() + " already present");
        }
        return storageDao.add(user);
    }
}
