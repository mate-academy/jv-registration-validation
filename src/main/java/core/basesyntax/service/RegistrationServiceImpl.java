package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH_LOGIN = 0;
    private static final int MIN_LENGTH_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can not be null!");
        }
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationException("All fields User must be not empty!");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("You are very young for this ;)");
        }
        if (user.getPassword().trim().length() < MIN_LENGTH_PASSWORD) {
            throw new RegistrationException("Your password is small. The password must be at least "
                    + MIN_LENGTH_PASSWORD + " characters long.");
        }
        if (user.getLogin().trim().length() == MIN_LENGTH_LOGIN) {
            throw new RegistrationException("Login cannot be empty.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("A user with the same login is already registered.");
        }
        return storageDao.add(user);
    }
}
