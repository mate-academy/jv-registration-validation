package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_CREDENTIAL_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new InvalidUserException("User cannot be null");
        }
        if (user.getLogin() == null) {
            throw new InvalidUserException("Login cannot be null");
        }
        if (user.getLogin().length() < MIN_CREDENTIAL_LENGTH) {
            throw new InvalidUserException("Login must be at least "
                    + MIN_CREDENTIAL_LENGTH + " characters long");
        }
        if (user.getPassword() == null) {
            throw new InvalidUserException("Password cannot be null");
        }
        if (user.getPassword().length() < MIN_CREDENTIAL_LENGTH) {
            throw new InvalidUserException("Password must be at least "
                    + MIN_CREDENTIAL_LENGTH + " characters long");
        }
        if (user.getAge() == null) {
            throw new InvalidUserException("Age cannot be null");
        }
        if (user.getAge() < 0) {
            throw new InvalidUserException("Age cannot be negative");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserException("User must be at least " + MIN_AGE + " years old");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidUserException("User with this login already exists");
        }
        return storageDao.add(user);
    }
}
