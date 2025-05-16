package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    static final int MIN_LENGTH = 6;
    static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new RegistrationException("User can't be null");
        }
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login can't be empty");
        }
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password can't be empty");
        }
        if (user.getAge() == null) {
            throw new RegistrationException("User's age can't be empty");
        }
        if (user.getLogin().length() < MIN_LENGTH) {
            throw new RegistrationException(
                    "Login must be at least " + MIN_LENGTH + " characters long.");
        }
        if (user.getPassword().length() < MIN_LENGTH) {
            throw new RegistrationException(
                    "Password must be at least " + MIN_LENGTH + " characters long.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException(
                    "User's age must be at least" + MIN_AGE + " years.");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException(
                    "User with login '" + user.getLogin() + "' already exists.");
        }
        return storageDao.add(user);
    }
}
