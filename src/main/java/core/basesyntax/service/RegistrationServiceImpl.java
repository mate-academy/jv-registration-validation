package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int DEFAULT_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User is null");
        }
        validateUser(user);
        return storageDao.add(user);
    }

    private void validateUser(User user) {
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("This login is already taken: " + user.getLogin());
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("Password can't be null");
        }
        if (user.getLogin().length() < DEFAULT_LENGTH) {
            throw new RegistrationException("Login must be at least 6 characters");
        }
        if (user.getPassword().length() < DEFAULT_LENGTH) {
            throw new RegistrationException("Password must be at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("Age can't be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("Can't register your age: "
                    + user.getAge() + " is below 18");
        }
    }
}
