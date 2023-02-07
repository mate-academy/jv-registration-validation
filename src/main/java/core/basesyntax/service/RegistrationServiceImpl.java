package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidRegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validation(user);
        return storageDao.add(user);
    }

    private void validation(User user) {
        if (user.getLogin() == null) {
            throw new InvalidRegistrationException("Login can't be null");
        }
        if (user.getLogin().isEmpty()) {
            throw new InvalidRegistrationException("Login cannot be empty");
        }
        if (user.getPassword() == null) {
            throw new InvalidRegistrationException("Password can't be null");
        }
        if (user.getPassword().length() < MIN_PASSWORD) {
            throw new InvalidRegistrationException("Password must consist of at least 6 "
                    + "characters");
        }
        if (user.getAge() == null) {
            throw new InvalidRegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidRegistrationException("Age must be at least 18");
        }
        User userFromDb = storageDao.get(user.getLogin());
        if (userFromDb != null) {
            throw new InvalidRegistrationException("User with login "
                    + userFromDb.getLogin() + "has already registered");
        }
    }
}
