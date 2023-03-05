package core.basesyntax.service;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_AGE = 18;
    private static final int MINIMUM_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null || user.getLogin() == null
                || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("Invalid data. User can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new RegistrationException("Login can't be empty");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new RegistrationException("Age can't be less than " + MINIMUM_AGE + "!");
        }
        if (user.getPassword().length() < MINIMUM_PASSWORD) {
            throw new RegistrationException("The password must contain at least "
                    + MINIMUM_PASSWORD + " characters");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
