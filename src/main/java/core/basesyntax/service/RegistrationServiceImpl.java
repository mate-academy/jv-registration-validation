package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_NUMBER_SYMBOLS = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        validateOfUserNull(user);
        validateLogin(user);
        validatePassword(user);
        validateAge(user);
        return storageDao.add(user);
    }

    private void validateAge(User user) {
        if (user.getAge() == null) {
            throw new RegistrationException("User's age cannot be null");
        }
        if (user.getAge() < MIN_AGE) {
            throw new RegistrationException("User must be older than " + MIN_AGE);
        }
    }

    private void validateLogin(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationException("User's login cannot be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationException("User already exists");
        }
        if (user.getLogin().length() < MIN_NUMBER_SYMBOLS) {
            throw new RegistrationException("User login must be at least  "
                    + MIN_NUMBER_SYMBOLS + " characters");
        }
    }

    private void validatePassword(User user) {
        if (user.getPassword() == null) {
            throw new RegistrationException("User's password cannot be null");
        }
        if (user.getPassword().length() < MIN_NUMBER_SYMBOLS) {
            throw new RegistrationException("User password must be at least  "
                    + MIN_NUMBER_SYMBOLS + " characters");
        }
    }

    private void validateOfUserNull(User user) {
        if (user == null) {
            throw new RegistrationException("User cannot be null");
        }
    }
}
